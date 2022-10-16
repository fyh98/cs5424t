package cs5424t.ysql.Transactions;


import cs5424t.ysql.DAO.*;
import cs5424t.ysql.Entities.*;
import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

@Service
public class SupplyChainTransaction {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public void newOrder(Integer warehouseId, Integer districtId, Integer customerId,
                         Integer itemTotalNum,
                        List<Integer> itemNumber, List<Integer> supplierWarehouse,
                         List<Integer> quantity){
        // query district obj from db
        District district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        Customer customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
        Warehouse warehouse = warehouseRepository.findById(warehouseId).get();

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
        order.setId(N);
        order.setDistrictId(districtId);
        order.setWarehouseId(warehouseId);
        order.setCustomerId(customerId);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setCarrierId(null);
        order.setNumOfItems(BigDecimal.valueOf(itemTotalNum));
        order.setStatus(BigDecimal.valueOf(O_ALL_LOCAL));

        // 3. Order number O ID, entry date O ENTRY D
        System.out.println(order.getId() + " " + order.getCreateTime());

        orderRepository.save(order);

//        int j = 1 / 0;

        double TOTAL_AMOUNT = 0.0;

        for(int i=0;i<itemTotalNum;i++){
            int curItemId = itemNumber.get(i);
            Stock curStock = stockRepository.findByWarehouseIdAndItemId(warehouseId, curItemId);
            Item curItem = itemRepository.findById(curItemId).get();
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
            orderLine.setOrderId(N);
            orderLine.setDistrictId(districtId);
            orderLine.setWarehouseId(warehouseId);
            orderLine.setId(i);
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void stockLevel(Integer warehouseId, Integer districtId, BigDecimal threshold, Integer numLastOrders) {
        // 1. N denote the value of the next available order number
        District district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        int N = district.getNextAvailOrderNum();

        // 2. S denote the set of items from the last L orders for district (W_ID,D_ID)
        List<OrderLine> orderLines;
        int numUnderThreshold = 0;
        for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
            orderLines = orderLineRepository.findByWarehouseIdAndDistrictIdAndOrderId(warehouseId, districtId, orderId);

            // 3. Output the total number of items in S where its stock quantity at W ID is below the threshold
            for (OrderLine orderLine : orderLines) {
                if (orderLine.getQuantity().compareTo(threshold) < 0) {
                    numUnderThreshold += 1;
                }
            }
        }
        System.out.println("Quantity under threshold: " + numUnderThreshold);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void popularItem(Integer warehouseId, Integer districtId, Integer numLastOrders) {
        System.out.println("District identifier: (" + warehouseId + ", " + districtId + ")");
        System.out.println("Number of Last Orders: " + numLastOrders);

        District district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        int N = district.getNextAvailOrderNum();


        List<Integer> popularItemIds = new ArrayList<>();
        for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
            Order order = orderRepository.findById(new OrderPK(warehouseId, districtId, orderId)).get();
            System.out.println("" + orderId + ": " + order.getCreateTime());

            int customerId = order.getCustomerId();
            Customer customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
            System.out.println("Placed by: " + customer.getFirstName() +
                    "." + customer.getMiddleName() +
                    "." + customer.getLastName());

            List<OrderLine> orderLines;
            orderLines = orderLineRepository.findByWarehouseIdAndDistrictIdAndOrderId(warehouseId, districtId, orderId);

            // most popular items in this order
            List<OrderLine> mostPopulars = new ArrayList<>();
            BigDecimal maxQuantity = new BigDecimal(-1);
            for (OrderLine ol : orderLines) {
                BigDecimal quantity = ol.getQuantity();
                if (quantity.compareTo(maxQuantity) == 0) {
                    mostPopulars.add(ol);
                }
                else if (quantity.compareTo(maxQuantity) > 0) {
                    mostPopulars = new ArrayList<>();
                    mostPopulars.add(ol);
                    maxQuantity = quantity;
                }
            }

            // output popular items and their quantity in this order
            for (OrderLine ol : mostPopulars) {
                int itemId = ol.getItemId();
                Item item = itemRepository.findById(itemId).get();
                System.out.println("Item name: " + item.getName() + "; Quantity: " + ol.getQuantity());

                popularItemIds.add(item.getId());
            }
        }

        // popular item percentage in examined orders
        for (int itemId : popularItemIds) {
            int count = 0;
            Item item = itemRepository.findById(itemId).get();
            for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
                List<OrderLine> orderLines;
                orderLines = orderLineRepository.findByWarehouseIdAndDistrictIdAndOrderId(warehouseId, districtId, orderId);
                for (OrderLine ol : orderLines) {
                    if (ol.getItemId() == itemId) {
                        count += 1;
                        break;
                    }
                }
            }

            String itemName = item.getName();
            double percentage = 0.0;
            if (numLastOrders != 0) {
                percentage = (count * 1.0) / numLastOrders * 100;
            }
            System.out.println("Item name: " + itemName + "; Percentage: " + percentage + "%");
        }
    }
}
