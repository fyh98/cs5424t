package cs5424t.ysql.Transactions;


import cs5424t.ysql.DAO.Xcnd48.*;
import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import cs5424t.ysql.Entities.Xcnd48.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SupplyChainTransaction48 {

    @Autowired
    @Qualifier("customerRepositoryXcnd48")
    private CustomerRepositoryXcnd48 customerRepository;

    @Autowired
    @Qualifier("districtRepositoryXcnd48")
    private DistrictRepositoryXcnd48 districtRepository;

    @Autowired
    @Qualifier("itemRepositoryXcnd48")
    private ItemRepositoryXcnd48 itemRepository;

    @Autowired
    @Qualifier("orderLineRepositoryXcnd48")
    private OrderLineRepositoryXcnd48 orderLineRepository;

    @Autowired
    @Qualifier("orderRepositoryXcnd48")
    private OrderRepositoryXcnd48 orderRepository;

    @Autowired
    @Qualifier("stockRepositoryXcnd48")
    private StockRepositoryXcnd48 stockRepository;

    @Autowired
    @Qualifier("warehouseRepositoryXcnd48")
    private WarehouseRepositoryXcnd48 warehouseRepository;
    
    @PersistenceContext
    @Qualifier("entityManagerXcnd48")
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void newOrder(Integer warehouseId, Integer districtId, Integer customerId,
                         Integer itemTotalNum,
                        List<Integer> itemNumber, List<Integer> supplierWarehouse,
                         List<Integer> quantity){
        // query district obj from db
        DistrictXcnd48 district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        CustomerXcnd48 customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
        WarehouseXcnd48 warehouse = warehouseRepository.findById(warehouseId).get();

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
        OrderXcnd48 order = new OrderXcnd48();
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
            StockXcnd48 curStock = stockRepository.findByWarehouseIdAndItemId(warehouseId, curItemId);
            ItemXcnd48 curItem = itemRepository.findById(curItemId).get();
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

            OrderLineXcnd48 orderLine = new OrderLineXcnd48();
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


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void payment(Integer warehouseId, Integer districtId, Integer customerId,
                        BigDecimal paymentAmount){
        // query district, customer, warehouse obj from db
        DistrictXcnd48 district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        CustomerXcnd48 customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
        WarehouseXcnd48 warehouse = warehouseRepository.findById(warehouseId).get();


        // 1.Update the warehouse C W ID by incrementing W YTD by paymentAmount
        warehouse.setYtd(warehouse.getYtd().add(paymentAmount));

        // 2. Update the district (C W ID,C D ID) by incrementing D YTD by paymentAmount
        district.setYtd(district.getYtd().add(paymentAmount));

        // 3. Update the customer (C W ID, C D ID, C ID) as follows:
        // Decrement C BALANCE by PAYMENT
        // Increment C YTD PAYMENT by PAYMENT
        // Increment C PAYMENT CNT by 1
        customer.setBalance(customer.getBalance().add(paymentAmount.negate()));
        customer.setYtdPayment(customer.getYtdPayment() + paymentAmount.doubleValue()); // TODO : double or decimal
        customer.setNumOfPayment(customer.getNumOfPayment() + 1);

        // output print
        System.out.println(" Customer Identifier :" + customer.toString());
        System.out.println(" Warehouse’s address : " + warehouse.getStreet1() + " " +  warehouse.getStreet2()
                + " " + warehouse.getCity() + " " + warehouse.getState() + " " + warehouse.getZipcode());
        System.out.println(" District’s address : " + district.getStreet1() + " " +  district.getStreet2()
                + " " + district.getCity() + " " + district.getState() + " " + district.getZipcode() );
        System.out.println(" Payment : " + paymentAmount);

    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void delivery(Integer warehouseId, Integer carrierId){
//       For DISTRICT NO = 1 to 10
        for(int districtId = 1; districtId <= 10; districtId++){
//       (a) Let N denote the value of the smallest order number O ID for district (W ID,DISTRICT NO)
//       with O CARRIER ID = null; i.e.,
//       N = min{t.O ID ∈ Order | t.O W ID = W ID, t.D ID = DIST RICT NO, t.O CARRIER ID = null}
//       Let X denote the order corresponding to order number N, and let C denote the customer
//       who placed this order
            OrderXcnd48 oldestOrder = orderRepository.findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(warehouseId,districtId);
            //Order oldestOrder = orderRepository.findTopByWarehouseIdAndDistrictIdAndCarrierIdOrderByIdAsc(warehouseId,districtId,null);
            Integer orderId = oldestOrder.getId();
//       (b) Update the order X by setting O CARRIER ID to CARRIER ID
            oldestOrder.setCarrierId(carrierId);
//       (c) Update all the order-lines in X by setting OL DELIVERY D to the current date and time
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            List<OrderLineXcnd48> allOrderLines = orderLineRepository.findAllByWarehouseIdAndDistrictIdAndOrderId(warehouseId,districtId,orderId);
            BigDecimal totalOrderLineAmount = new BigDecimal(0);
            for(OrderLineXcnd48 orderLine : allOrderLines){
                orderLine.setDeliveryDate(currentTime);
                totalOrderLineAmount.add(orderLine.getTotalPrice());
            }
//       (d) Update customer C as follows:
//       Increment C BALANCE by B, where B denote the sum of OL AMOUNT for all the items placed in order X
//       Increment C DELIVERY CNT by 1
            Integer customerId = oldestOrder.getCustomerId();
            CustomerXcnd48 customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
            customer.setBalance(customer.getBalance().add(totalOrderLineAmount));
            customer.setNumOfDelivery(customer.getNumOfDelivery() + 1);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void orderStatus(Integer warehouseId, Integer districtId, Integer customerId){
        CustomerXcnd48 customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
        // 1. Customer’s name (C FIRST, C MIDDLE, C LAST), balance C BALANCE
        System.out.println("Customer's name : " + customer.getFirstName()
                + " " + customer.getMiddleName() + " " + customer.getLastName() + " balance : " +
                customer.getBalance());

//        2. For the customer’s last order
//        (a) Order number O ID
//        (b) Entry date and time O ENTRY D
//        (c) Carrier identifier O CARRIER ID
        OrderXcnd48 lastOrder = orderRepository.findTopByCustomerIdOrderByCreateTimeDesc(customerId);
        Integer orderId = lastOrder.getId();
        System.out.println("Order number : " + lastOrder.getId() + " Entry Date and time : " +
                lastOrder.getCreateTime() + " Carrier identifier : " + lastOrder.getCarrierId());
        System.out.println("-----------------------------------------------------------------");

//        3. For each item in the customer’s last order
//        (a) Item number OL I ID
//        (b) Supplying warehouse number OL SUPPLY W ID
//        (c) Quantity ordered OL QUANTITY
//        (d) Total price for ordered item OL AMOUNT
//        (e) Data and time of delivery OL DELIVERY D
        List<OrderLineXcnd48> allOrderLines = orderLineRepository.findAllByWarehouseIdAndDistrictIdAndOrderId(warehouseId,districtId,orderId);
        for(OrderLineXcnd48 orderLine : allOrderLines){
            System.out.println("Item number : " + orderLine.getItemId());
            System.out.println("Supplying warehouse number : " + orderLine.getWarehouseId());
            System.out.println("Quantity ordered : " + orderLine.getQuantity());
            System.out.println("Total price for ordered item : " + orderLine.getTotalPrice());
            System.out.println("Data and time of delivery : " + orderLine.getDeliveryDate());
            System.out.println("-----------------------------------------------------------------");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void stockLevel(Integer warehouseId, Integer districtId, BigDecimal threshold, Integer numLastOrders) {
        // 1. N denote the value of the next available order number
        DistrictXcnd48 district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        int N = district.getNextAvailOrderNum();

        // 2. S denote the set of items from the last L orders for district (W_ID,D_ID)
        List<OrderLineXcnd48> orderLines;
        int numUnderThreshold = 0;
        for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
            orderLines = orderLineRepository.findAllByWarehouseIdAndDistrictIdAndOrderId(warehouseId, districtId, orderId);

            // 3. Output the total number of items in S where its stock quantity at W ID is below the threshold
            for (OrderLineXcnd48 orderLine : orderLines) {
                if (orderLine.getQuantity().compareTo(threshold) < 0) {
                    numUnderThreshold += 1;
                }
            }
        }
        System.out.println("Quantity under threshold: " + numUnderThreshold);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void popularItem(Integer warehouseId, Integer districtId, Integer numLastOrders) {
        System.out.println("District identifier: (" + warehouseId + ", " + districtId + ")");
        System.out.println("Number of Last Orders: " + numLastOrders);

        DistrictXcnd48 district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        int N = district.getNextAvailOrderNum();


        List<Integer> popularItemIds = new ArrayList<>();
        HashMap<Integer, List<OrderLineXcnd48>> orderlineMap = new HashMap<>();
        for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
            OrderXcnd48 order = orderRepository.findById(new OrderPK(warehouseId, districtId, orderId)).get();
            System.out.println("" + orderId + ": " + order.getCreateTime());

            int customerId = order.getCustomerId();
            CustomerXcnd48 customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
            System.out.println("Placed by: " + customer.getFirstName() +
                    "." + customer.getMiddleName() +
                    "." + customer.getLastName());

            List<OrderLineXcnd48> orderLines;
            orderLines = orderLineRepository.findAllByWarehouseIdAndDistrictIdAndOrderId(warehouseId, districtId, orderId);
            orderlineMap.put(orderId, orderLines);

            // most popular items in this order
            List<OrderLineXcnd48> mostPopulars = new ArrayList<>();
            BigDecimal maxQuantity = new BigDecimal(-1);
            for (OrderLineXcnd48 ol : orderLines) {
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
            for (OrderLineXcnd48 ol : mostPopulars) {
                int itemId = ol.getItemId();
                ItemXcnd48 item = itemRepository.findById(itemId).get();
                System.out.println("Item name: " + item.getName() + "; Quantity: " + ol.getQuantity());

                popularItemIds.add(item.getId());
            }
        }

        // popular item percentage in examined orders
        for (int itemId : popularItemIds) {
            int count = 0;
            ItemXcnd48 item = itemRepository.findById(itemId).get();
            for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
                List<OrderLineXcnd48> orderLines = orderlineMap.get(orderId);
                for (OrderLineXcnd48 ol : orderLines) {
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void topBalance(){
    	HashMap<Integer, String> warehouse_id_name = new HashMap<Integer, String>();
    	HashMap<Integer, String> district_id_name = new HashMap<Integer, String>();
    	String sql = "select c_first, c_middle, c_last, c_balance, c_w_id, c_d_id from customer_ysql order by c_balance desc limit 10";
    	String districtName, warehouseName;
    	String firstname, middlename, lastname;
    	BigDecimal balance;
    	Integer warehouseId, districtId;
    	List<Object[]> result = em.createNativeQuery(sql).getResultList();
    	for(int i = 0; i < result.size(); ++ i) {
    		firstname = (String) (result.get(i)[0]);
    		middlename = (String) (result.get(i)[1]);
    		lastname = (String) (result.get(i)[2]);
    		balance = (BigDecimal) (result.get(i)[3]);
    		warehouseId = (Integer) (result.get(i)[4]);
    		districtId = (Integer) (result.get(i)[5]);
    		System.out.println(firstname + " " + middlename + " " + lastname);
    		System.out.println(balance);
    		warehouseName = warehouse_id_name.get(warehouseId);
    		districtName = district_id_name.get(districtId);
    		if(warehouseName == null) {
    			WarehouseXcnd48 warehouse = warehouseRepository.findById(warehouseId).get();
    			warehouseName = warehouse.getName();
    			warehouse_id_name.put(warehouseId, warehouseName);
    		}
    		if(districtName == null) {
    			DistrictXcnd48 district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
    			districtName = district.getName();
    			district_id_name.put(districtId, districtName);
    		}
    		System.out.println(warehouseName);
    		System.out.println(districtName);
    	}
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void relatedCustomer(Integer warehouseId, Integer districtId, Integer customerId){
    	//1. Customer identifier (C W ID, C D ID, C ID)
    	System.out.println(warehouseId + " " + districtId + " " + customerId);
    	
    	String cte_sql = "with cte as (select distinct o.o_w_id, o.o_d_id, o.o_c_id, ol.ol_i_id from order_ysql o join order_line_ysql ol on o.o_w_id = ol.ol_w_id and " +
        		"o.o_d_id = ol.ol_d_id and o.o_id = ol.ol_o_id) ";
    	String query1 = "(select distinct ol_i_id from cte where cte.o_w_id = " + warehouseId.intValue() + " and cte.o_d_id = " +
        		districtId.intValue() + " and cte.o_c_id = " + customerId.intValue() + ")query1";
    	String query2 = "(select distinct o_w_id, o_d_id, o_c_id, ol_i_id from cte where o_w_id != " + warehouseId.intValue() + ")query2";
    	String join = " select o_w_id, o_d_id, o_c_id from " + query1 + " join " + query2 + " on query1.ol_i_id = query2.ol_i_id group by o_w_id, o_d_id, o_c_id having count(*) >= 2";
    	String query = cte_sql + join;
    	List<Object[]> result = em.createNativeQuery(query).getResultList();
    	for(Object[] object: result) {
    		// Output the identifier of C
    		System.out.println(object[0] + " " + object[1] + " " + object[2]);
    	}
    }
}
