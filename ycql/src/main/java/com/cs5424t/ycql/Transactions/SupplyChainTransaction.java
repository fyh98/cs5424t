package com.cs5424t.ycql.Transactions;


import com.cs5424t.ycql.DAO.*;
import com.cs5424t.ycql.Entities.*;
import com.cs5424t.ycql.Entities.PrimaryKeys.*;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class SupplyChainTransaction {

    @Autowired
    CqlSession ycqlSession;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    public void newOrder(Integer warehouseId, Integer districtId, Integer customerId,
                         Integer itemTotalNum,
                        List<Integer> itemNumber, List<Integer> supplierWarehouse,
                         List<Integer> quantity){
        // query district obj from db
        District district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        Customer customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
        Warehouse warehouse = warehouseRepository.findById(new WarehousePK(warehouseId)).get();


        // 1. Customer identifier (W ID, D ID, C ID), lastname C LAST,
        // credit C CREDIT, discount C DISCOUNT
        System.out.println(warehouseId + " " + districtId + " " + customerId);
        System.out.println(customer.getLastName() + " " +
                    customer.getCreditLimit() + " " +
                    customer.getDiscountRate());

        // 2. Warehouse tax rate W TAX, District tax rate D TAX
        System.out.println(warehouse.getTax() + " " + district.getTax());

        // get next id for order
        int N = district.getNextAvailOrderNum();

        // update district
        district.setNextAvailOrderNum(N + 1);
        districtRepository.save(district);

        // calculate O_ALL_LOCAL
        int  O_ALL_LOCAL = 1;
        for(Integer tmp : supplierWarehouse){
            if(tmp.intValue() != warehouseId.intValue()) {
                O_ALL_LOCAL = 0;
                break;
            }
        }

        // create Order object
        Order order = new Order();
        OrderPK orderPK = new OrderPK();
        orderPK.setId(N);
        orderPK.setDistrictId(districtId);
        orderPK.setWarehouseId(warehouseId);
        order.setOrderPK(orderPK);
        order.setCustomerId(customerId);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setCarrierId(null);
        order.setNumOfItems(BigDecimal.valueOf(itemTotalNum));
        order.setStatus(BigDecimal.valueOf(O_ALL_LOCAL));

        // 3. Order number O ID, entry date O ENTRY D
        System.out.println(order.getOrderPK().getId() + " " + order.getCreateTime());

        orderRepository.save(order);

        double TOTAL_AMOUNT = 0.0;

        for(int i=0;i<itemTotalNum;i++){
            int curItemId = itemNumber.get(i);
            StockPK stockPK = new StockPK();
            stockPK.setWarehouseId(warehouseId);
            stockPK.setItemId(curItemId);
            Stock curStock = stockRepository.findById(stockPK).get();
            Item curItem = itemRepository.findById(new ItemPK(curItemId)).get();
            int S_QUANTITY = curStock.getStockNum().intValue();
            int ADJUSTED_QTY = S_QUANTITY - quantity.get(i);
            if(ADJUSTED_QTY < 10) ADJUSTED_QTY += 100;
            curStock.setStockNum(BigDecimal.valueOf(ADJUSTED_QTY));
            curStock.setYtd(BigDecimal.valueOf(curStock.getYtd().intValue() + quantity.get(i)));
            curStock.setNumOfOrder(curStock.getNumOfOrder() + 1);
            if(supplierWarehouse.get(i).intValue() != warehouseId.intValue()){
                curStock.setNumOfRemoteOrder(curStock.getNumOfRemoteOrder() + 1);
            }
            stockRepository.save(curStock);

            BigDecimal itemTotalPrice = curItem.getPrice()
                    .multiply(BigDecimal.valueOf(quantity.get(i)));

            TOTAL_AMOUNT += itemTotalPrice.doubleValue();

            OrderLine orderLine = new OrderLine();
            OrderLinePK orderLinePK = new OrderLinePK();
            orderLinePK.setOrderId(N);
            orderLinePK.setDistrictId(districtId);
            orderLinePK.setWarehouseId(warehouseId);
            orderLinePK.setId(i);
            orderLine.setOrderLinePK(orderLinePK);
            orderLine.setItemId(itemNumber.get(i));
            orderLine.setSupplyWarehouseId(supplierWarehouse.get(i));
            orderLine.setQuantity(BigDecimal.valueOf(quantity.get(i)));
            orderLine.setTotalPrice(itemTotalPrice);
            orderLine.setDeliveryDate(null);

            switch (i) {
                case 0 -> orderLine.setExtraData(curStock.getDistrict1());
                case 1 -> orderLine.setExtraData(curStock.getDistrict2());
                case 2 -> orderLine.setExtraData(curStock.getDistrict3());
                case 3 -> orderLine.setExtraData(curStock.getDistrict4());
                case 4 -> orderLine.setExtraData(curStock.getDistrict5());
                case 5 -> orderLine.setExtraData(curStock.getDistrict6());
                case 6 -> orderLine.setExtraData(curStock.getDistrict7());
                case 7 -> orderLine.setExtraData(curStock.getDistrict8());
                case 8 -> orderLine.setExtraData(curStock.getDistrict9());
                case 9 -> orderLine.setExtraData(curStock.getDistrict10());
                default -> {
                }
            }

            orderLineRepository.save(orderLine);

            //  5. For each ordered item ITEM NUMBER[i], i ∈ [1, NUM IT EMS]
            //     (a) ITEM NUMBER[i]
            //     (b) I NAME
            //     (c) SUPPLIER WAREHOUSE[i]
            //     (d) QUANTITY[i]
            //     (e) OL AMOUNT
            //     (f) S QUANTITY
            System.out.println(i + " " + curItem.getName() + " "
                    + supplierWarehouse.get(i) + " " + quantity.get(i) + " "
                    + orderLine.getTotalPrice() + " " + S_QUANTITY);
        }


        TOTAL_AMOUNT = TOTAL_AMOUNT *
                (1 + district.getTax().doubleValue() + warehouse.getTax().doubleValue())
                * (1 - customer.getDiscountRate().doubleValue());

        // 4. Number of items NUM ITEMS, Total amount for order TOTAL AMOUNT
        System.out.println(itemTotalNum + " " + TOTAL_AMOUNT);
    }
}
