package com.cs5424t.ycql.Transactions;


import com.cs5424t.ycql.DAO.*;
import com.cs5424t.ycql.Entities.*;
import com.cs5424t.ycql.Entities.PrimaryKeys.*;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
        long start = System.currentTimeMillis();
        // query district obj from db
        District district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        Customer customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
        Warehouse warehouse = warehouseRepository.findById(new WarehousePK(warehouseId)).get();

        long first = System.currentTimeMillis();
        System.out.println("query district/cutomer/warehouse: " + (first - start));

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

        long tmp2 = System.currentTimeMillis();

//        districtRepository.save(district);
        districtRepository.updateNextAvalNumById(N+1, warehouseId, districtId);

        long second = System.currentTimeMillis();
        System.out.println("save district: " + (second - tmp2));

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
        order.setCarrierId(-1);//TODO : -1
        order.setNumOfItems(BigDecimal.valueOf(itemTotalNum));
        order.setStatus(BigDecimal.valueOf(O_ALL_LOCAL));

        // 3. Order number O ID, entry date O ENTRY D
        System.out.println(order.getOrderPK().getId() + " " + order.getCreateTime());

        orderRepository.save(order);

        double TOTAL_AMOUNT = 0.0;

        long third = System.currentTimeMillis();
        System.out.println("save order: " + (third - second));

        List<Stock> stockCache = new ArrayList<>();
        List<OrderLine> orderLineCache = new ArrayList<>();

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

//            stockRepository.save(curStock);
            stockCache.add(curStock);

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

//            orderLineRepository.save(orderLine);
            orderLineCache.add(orderLine);

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

        stockRepository.saveAll(stockCache);
        orderLineRepository.saveAll(orderLineCache);

        long fourth = System.currentTimeMillis();
        System.out.println("after saving all stocks: " + (fourth - third));

        TOTAL_AMOUNT = TOTAL_AMOUNT *
                (1 + district.getTax().doubleValue() + warehouse.getTax().doubleValue())
                * (1 - customer.getDiscountRate().doubleValue());

        // 4. Number of items NUM ITEMS, Total amount for order TOTAL AMOUNT
        System.out.println(itemTotalNum + " " + TOTAL_AMOUNT);
    }


    public void payment(Integer warehouseId, Integer districtId, Integer customerId,
                        BigDecimal paymentAmount){
        // query district, customer, warehouse obj from db
        District district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        Customer customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
        Warehouse warehouse = warehouseRepository.findById(new WarehousePK(warehouseId)).get();


        // 1.Update the warehouse C W ID by incrementing W YTD by paymentAmount
        warehouse.setYtd(warehouse.getYtd().add(paymentAmount));
        warehouseRepository.save(warehouse);

        // 2. Update the district (C W ID,C D ID) by incrementing D YTD by paymentAmount
        district.setYtd(district.getYtd().add(paymentAmount));
        districtRepository.save(district);

        // 3. Update the customer (C W ID, C D ID, C ID) as follows:
        // Decrement C BALANCE by PAYMENT
        // Increment C YTD PAYMENT by PAYMENT
        // Increment C PAYMENT CNT by 1
        customer.setBalance(customer.getBalance().add(paymentAmount.negate()));
        customer.setYtdPayment(customer.getYtdPayment() + paymentAmount.floatValue()); // TODO : double or decimal
        customer.setNumOfPayment(customer.getNumOfPayment() + 1);
        customerRepository.save(customer);

        // output print
        System.out.println(" Customer Identifier :" + customer.toString());
        System.out.println(" Warehouse’s address : " + warehouse.getStreet1() + " " +  warehouse.getStreet2()
                + " " + warehouse.getCity() + " " + warehouse.getState() + " " + warehouse.getZipcode());
        System.out.println(" District’s address : " + district.getStreet1() + " " +  district.getStreet2()
                + " " + district.getCity() + " " + district.getState() + " " + district.getZipcode() );
        System.out.println(" Payment : " + paymentAmount);

    }


    public void delivery(Integer warehouseId, Integer carrierId){
//       For DISTRICT NO = 1 to 10
        for(int districtId = 1; districtId <= 10; districtId++){
//       (a) Let N denote the value of the smallest order number O ID for district (W ID,DISTRICT NO)
//       with O CARRIER ID = null; i.e.,
//       N = min{t.O ID ∈ Order | t.O W ID = W ID, t.D ID = DIST RICT NO, t.O CARRIER ID = null}
//       Let X denote the order corresponding to order number N, and let C denote the customer
//       who placed this order
            Order oldestOrder = orderRepository.findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(warehouseId,districtId);
            //Order oldestOrder = orderRepository.findTopByWarehouseIdAndDistrictIdAndCarrierIdOrderByIdAsc(warehouseId,districtId,null);
            if(oldestOrder == null)
                continue;
            OrderPK oldestOrderPk = oldestOrder.getOrderPK();
            Integer orderId = oldestOrderPk.getId();
//       (b) Update the order X by setting O CARRIER ID to CARRIER ID
            oldestOrder.setCarrierId(carrierId);
            orderRepository.save(oldestOrder);
//       (c) Update all the order-lines in X by setting OL DELIVERY D to the current date and time
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            List<OrderLine> allOrderLines = orderLineRepository.findAllByWarehouseIdAndDistrictIdAndOrderId(warehouseId,districtId,orderId);
            BigDecimal totalOrderLineAmount = new BigDecimal(0);
            for(OrderLine orderLine : allOrderLines){
                orderLine.setDeliveryDate(currentTime);
                totalOrderLineAmount.add(orderLine.getTotalPrice());
                orderLineRepository.save(orderLine);
            }
//       (d) Update customer C as follows:
//       Increment C BALANCE by B, where B denote the sum of OL AMOUNT for all the items placed in order X
//       Increment C DELIVERY CNT by 1
            Integer customerId = oldestOrder.getCustomerId();
            Customer customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
            customer.setBalance(customer.getBalance().add(totalOrderLineAmount));
            customer.setNumOfDelivery(customer.getNumOfDelivery() + 1);
            customerRepository.save(customer);
        }
    }


    public void orderStatus(Integer warehouseId, Integer districtId, Integer customerId){
        Customer customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
        // 1. Customer’s name (C FIRST, C MIDDLE, C LAST), balance C BALANCE
        System.out.println("Customer's name : " + customer.getFirstName()
                + " " + customer.getMiddleName() + " " + customer.getLastName() + " balance : " +
                customer.getBalance());

//        2. For the customer’s last order
//        (a) Order number O ID
//        (b) Entry date and time O ENTRY D
//        (c) Carrier identifier O CARRIER ID
        Integer lastOrderId = orderRepository.findLastOrderId(warehouseId,districtId,customerId);
        Order lastOrder = orderRepository.findLastOrder(warehouseId,districtId,lastOrderId);
        OrderPK lastOrderPk = lastOrder.getOrderPK();
        Integer orderId = lastOrderPk.getId();
        System.out.println("Order number : " + lastOrderPk.getId() + " Entry Date and time : " +
                lastOrder.getCreateTime() + " Carrier identifier : " + lastOrder.getCarrierId());
        System.out.println("-----------------------------------------------------------------");

//        3. For each item in the customer’s last order
//        (a) Item number OL I ID
//        (b) Supplying warehouse number OL SUPPLY W ID
//        (c) Quantity ordered OL QUANTITY
//        (d) Total price for ordered item OL AMOUNT
//        (e) Data and time of delivery OL DELIVERY D
        List<OrderLine> allOrderLines = orderLineRepository.findAllByWarehouseIdAndDistrictIdAndOrderId(warehouseId,districtId,orderId);
        for(OrderLine orderLine : allOrderLines){
            OrderLinePK orderLinePK = orderLine.getOrderLinePK();
            System.out.println("Item number : " + orderLine.getItemId());
            System.out.println("Supplying warehouse number : " + orderLinePK.getWarehouseId());
            System.out.println("Quantity ordered : " + orderLine.getQuantity());
            System.out.println("Total price for ordered item : " + orderLine.getTotalPrice());
            System.out.println("Data and time of delivery : " + orderLine.getDeliveryDate());
            System.out.println("-----------------------------------------------------------------");
        }
    }


    public void stockLevel(Integer warehouseId, Integer districtId, BigDecimal threshold, Integer numLastOrders) {
        // 1. N denote the value of the next available order number
        District district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        int N = district.getNextAvailOrderNum();

        // 2. S denote the set of items from the last L orders for district (W_ID,D_ID)
        List<OrderLine> orderLines;
        int numUnderThreshold = 0;
        for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
            orderLines = orderLineRepository.findAllByWarehouseIdAndDistrictIdAndOrderId(warehouseId, districtId, orderId);

            // 3. Output the total number of items in S where its stock quantity at W ID is below the threshold
            for (OrderLine orderLine : orderLines) {
                if (orderLine.getQuantity().compareTo(threshold) < 0) {
                    numUnderThreshold += 1;
                }
            }
        }
        System.out.println("Quantity under threshold: " + numUnderThreshold);
    }


    public void popularItem(Integer warehouseId, Integer districtId, Integer numLastOrders) {
        System.out.println("District identifier: (" + warehouseId + ", " + districtId + ")");
        System.out.println("Number of Last Orders: " + numLastOrders);

        District district = districtRepository.findById(new DistrictPK(warehouseId, districtId)).get();
        int N = district.getNextAvailOrderNum();

        List<Integer> popularItemIds = new ArrayList<>();
        HashMap<Integer, List<OrderLine>> orderlineMap = new HashMap<>();
        for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
            Order order = orderRepository.findById(new OrderPK(warehouseId, districtId, orderId)).get();
            System.out.println("" + orderId + ": " + order.getCreateTime());

            int customerId = order.getCustomerId();
            Customer customer = customerRepository.findById(new CustomerPK(warehouseId, districtId, customerId)).get();
            System.out.println("Placed by: " + customer.getFirstName() +
                    "." + customer.getMiddleName() +
                    "." + customer.getLastName());

            List<OrderLine> orderLines;
            orderLines = orderLineRepository.findAllByWarehouseIdAndDistrictIdAndOrderId(warehouseId, districtId, orderId);
            orderlineMap.put(orderId, orderLines);

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
                Item item = itemRepository.findById(new ItemPK(itemId)).get();
                System.out.println("Item name: " + item.getName() + "; Quantity: " + ol.getQuantity());

                popularItemIds.add(item.getItemPK().getId());
            }
        }

        // popular item percentage in examined orders
        for (int itemId : popularItemIds) {
            int count = 0;
            Item item = itemRepository.findById(new ItemPK(itemId)).get();
            for (int orderId = Math.max(N-numLastOrders, 0); orderId < N; orderId++) {
                List<OrderLine> orderLines;
                orderLines = orderlineMap.get(orderId);
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

    public void topBalance() {
    	// all customers
    	List<Customer> allCustomers = customerRepository.findAllCustomer();
    	Collections.sort(allCustomers);
    	//top 10 customers 
    	Customer[] topCustomers = new Customer[10];
    	for(int i = 0; i < 10; ++i) {
    		topCustomers[i] = allCustomers.get(i);
    		System.out.println(allCustomers.get(i).getBalance());
    	}
    	
    	HashMap<Integer, String> warehouseNamesMap = new HashMap<>();
    	HashMap<DistrictPK, String> districtNamesMap = new HashMap<>();
    	for(Customer customer: topCustomers) {
    		//(a) Name of customer (C FIRST, C MIDDLE, C LAST)
    		System.out.println(customer.getFirstName() + " "+customer.getMiddleName() + " " + customer.getLastName());

    		//(b) Balance of customer’s outstanding payment C BALANCE
    		System.out.println(customer.getBalance());
    		
    		CustomerPK customerPK = customer.getCustomerPK();
    		Integer warehouseId = customerPK.getWarehouseId();
    		Integer districtId = customerPK.getDistrictId();
    		//(c) Warehouse name of customer W NAME
    		if(warehouseNamesMap.get(warehouseId)!=null) {
    			System.out.println(warehouseNamesMap.get(warehouseId));
    		}
    		else {
    			Warehouse warehouse = warehouseRepository.findById(new WarehousePK(warehouseId)).get();
    			System.out.println(warehouse.getName());
    			warehouseNamesMap.put(warehouseId, warehouse.getName());
    		}
    		//(d) District name of customer D NAME
    		DistrictPK districtPK = new DistrictPK(warehouseId, districtId);
    		if(districtNamesMap.get(districtPK)!=null) {
    			System.out.println(districtNamesMap.get(districtPK));
    		}
    		else {
    			District district = districtRepository.findById(districtPK).get();
    			System.out.println(district.getName());
    			districtNamesMap.put(districtPK, district.getName());
    		}
    	}
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void relatedCustomer(Integer warehouseId, Integer districtId, Integer customerId) {
    	//1. Customer identifier (C W ID, C D ID, C ID)
    	System.out.println(warehouseId.intValue() + " " + districtId.intValue() + " " + customerId.intValue());
    	// get all orders
    	List<Order> allOrders = orderRepository.findAll();
    	// get all orderLines
    	List<OrderLine> allOrderLines = orderLineRepository.findAll();
    	
    	//first step of hash join, build phase
    	HashMap<OrderPK, Order> orderHashmap = new HashMap<OrderPK, Order>();
    	for(Order order: allOrders) {
    		orderHashmap.put(order.getOrderPK(), order);
    	}
    	// itemMap stores all items of the given customer
    	HashMap<Integer, Set<CustomerPK>> itemMap = new HashMap<>();
    	// join order with orderLine to compute all items of the given customer
    	for(OrderLine orderLine: allOrderLines) {
    		if(orderLine.getOrderLinePK().getWarehouseId().intValue() != warehouseId.intValue()) {
    			continue;
    		}
    		if(orderLine.getOrderLinePK().getDistrictId().intValue() != districtId.intValue()) {
    			continue;
    		}
    		OrderLinePK curOrderLinePK = orderLine.getOrderLinePK();
    		OrderPK curOrderPK = new OrderPK(curOrderLinePK.getWarehouseId(), curOrderLinePK.getDistrictId(), curOrderLinePK.getId());
    		Order correspondingOrder = orderHashmap.get(curOrderPK);
    		if((correspondingOrder == null) || (correspondingOrder.getCustomerId().intValue() != customerId.intValue())) {
    			continue;
    		}
    		else {
    			if(itemMap.get(orderLine.getItemId()) == null) {
    				Set<CustomerPK> customerSet = new HashSet<>();
    				itemMap.put(orderLine.getItemId(), customerSet);
    			}
    		}
    	}
    	
    	for(OrderLine orderLine: allOrderLines) {
    		if(orderLine.getOrderLinePK().getWarehouseId().intValue() == warehouseId.intValue()) {
    			continue;
    		}
    		OrderLinePK curOrderLinePK = orderLine.getOrderLinePK();
    		OrderPK curOrderPK = new OrderPK(curOrderLinePK.getWarehouseId(), curOrderLinePK.getDistrictId(), curOrderLinePK.getId());
    		Order correspondingOrder = orderHashmap.get(curOrderPK);
    		if(correspondingOrder == null) {
    			continue;
    		}
    		else {
    			Set<CustomerPK> customerSet = itemMap.get(orderLine.getItemId());
    			if(customerSet == null) {
    				continue;
    			}
    			CustomerPK curCustomerPK = new CustomerPK(orderLine.getOrderLinePK().getWarehouseId(), orderLine.getOrderLinePK().getDistrictId(),
    					correspondingOrder.getCustomerId());
    			customerSet.add(curCustomerPK);
    		}
    	}
    	
    	String temp = "";
    	HashMap<CustomerPK, String> recorder = new HashMap<>();
    	Iterator<Entry<Integer, Set<CustomerPK>>> iterator = itemMap.entrySet().iterator();
    	while(iterator.hasNext()) {
    		Entry<Integer, Set<CustomerPK>> entry = (Entry<Integer, Set<CustomerPK>>)iterator.next();
    		Set<CustomerPK> customerSet = entry.getValue();
    		for(CustomerPK customer: customerSet) {
    			if(recorder.get(customer) != null) {
    				//(a) Output the identifier of C
    				System.out.println(customer.getWarehouseId() + " " + customer.getDistrictId() + " " + customer.getId());
    			}
    			else {
    				recorder.put(customer, temp);
    			}
    		}
    	}
    }

    public List<Object> measurePerformance(){
        List<Object> res = new ArrayList<>();

        //i. select sum(W YTD) from Warehouse
        BigDecimal sum_w_ytd = warehouseRepository.findSumWYtd();

        //ii. select sum(D YTD), sum(D NEXT O ID) from District
        BigDecimal sum_d_ytd = districtRepository.findSumDYtd();
        Integer sum_d_next_o_id = districtRepository.findSumDNextOId();

        //iii. select sum(C BALANCE), sum(C YTD PAYMENT), sum(C PAYMENT CNT), sum(C DELIVERY CNT)
        //from Customer
        BigDecimal sum_c_balance = customerRepository.findSumCBalance();
        Float sum_c_ytd_payment = customerRepository.findSumCYtdPayment();
        Integer sum_c_payment_cnt = customerRepository.findCPaymentCnt();
        Integer sum_c_delivery_cnt = customerRepository.findCDeliveryCnt();

        //iv. select max(O ID), sum(O OL CNT) from Order
        Integer max_o_id = orderRepository.findMaxOId();
        Integer sum_o_ol_cnt = orderRepository.findSumOOlCnt();

        //v. select sum(OL AMOUNT), sum(OL QUANTITY) from Order-Line
        BigDecimal sum_ol_amount = orderLineRepository.findSumOlAmount();
        BigDecimal sum_ol_quantity = orderLineRepository.findSumOlQuantity();

        //vi. select sum(S QUANTITY), sum(S YTD), sum(S ORDER CNT), sum(S REMOTE CNT) from
        //Stock
        BigDecimal sum_s_quantity = stockRepository.findSumSQuantity();
        BigDecimal sum_s_ytd = stockRepository.findSumSYtd();
        Integer sum_s_order_cnt = stockRepository.findSumSOrderCnt();
        Integer sum_s_remote_cnt = stockRepository.findSumSRemoteCnt();

        res.add(sum_w_ytd);
        res.add(sum_d_ytd);
        res.add(sum_d_next_o_id);
        res.add(sum_c_balance);
        res.add(sum_c_ytd_payment);
        res.add(sum_c_payment_cnt);
        res.add(sum_c_delivery_cnt);
        res.add(max_o_id);
        res.add(sum_o_ol_cnt);
        res.add(sum_ol_amount);
        res.add(sum_ol_quantity);
        res.add(sum_s_quantity);
        res.add(sum_s_ytd);
        res.add(sum_s_order_cnt);
        res.add(sum_s_remote_cnt);


        return res;

    }

}
