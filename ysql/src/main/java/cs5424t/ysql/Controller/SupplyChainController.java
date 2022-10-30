package cs5424t.ysql.Controller;

import cs5424t.ysql.Transactions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sc")
public class SupplyChainController {

    @Autowired
    SupplyChainTransaction45 scServiceXcnd45;

    @Autowired
    SupplyChainTransaction46 scServiceXcnd46;

    @Autowired
    SupplyChainTransaction47 scServiceXcnd47;

    @Autowired
    SupplyChainTransaction48 scServiceXcnd48;

    @Autowired
    SupplyChainTransaction49 scServiceXcnd49;

    @RequestMapping("/newOrder")
    public String newOrder45(){
        int C_ID = 321;
        int W_ID = 3;
        int D_ID = 10;
        int M = 15;
        Integer[] tmp1 = {28350, 15967, 80615, 81314, 42983, 44732, 4996,
                15068, 40630, 42715, 54499, 64851, 88642, 89827, 97335};

        Integer[] tmp2 = {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};

        Integer[] tmp3 = {9, 9, 6, 6, 7, 2, 2, 4, 6, 5, 10, 9, 4, 4, 1};

        List<Integer> itemNumber = new ArrayList<>(Arrays.asList(tmp1));
        List<Integer> supplier = new ArrayList<>(Arrays.asList(tmp2));
        List<Integer> quantity = new ArrayList<>(Arrays.asList(tmp3));

        long start = System.currentTimeMillis();

        scServiceXcnd45.newOrder(W_ID, D_ID, C_ID, M, itemNumber, supplier, quantity);

        long end = System.currentTimeMillis();

        return "Done: " + (end - start);
    }

    @RequestMapping("/payment")
    public String payment(){
        int C_ID = 321;
        int W_ID = 3;
        int D_ID = 10;
        BigDecimal paymentAmount = new BigDecimal("100000");
        long start = System.currentTimeMillis();

        scServiceXcnd45.payment(W_ID,D_ID,C_ID,paymentAmount);
//        scServiceXcnd46.payment(W_ID,D_ID,C_ID,paymentAmount);
//        scServiceXcnd47.payment(W_ID,D_ID,C_ID,paymentAmount);
//        scServiceXcnd48.payment(W_ID,D_ID,C_ID,paymentAmount);
//        scServiceXcnd49.payment(W_ID,D_ID,C_ID,paymentAmount);

        long end = System.currentTimeMillis();

        return "Done: " + (end - start);
    }

    @RequestMapping("/delivery")
    public String delivery(){
        int W_ID = 3;
        int Carrier_ID = 10;
        long start = System.currentTimeMillis();
        scServiceXcnd45.delivery(W_ID, Carrier_ID);
//        scServiceXcnd46.delivery(W_ID, Carrier_ID);
//        scServiceXcnd47.delivery(W_ID, Carrier_ID);
//        scServiceXcnd48.delivery(W_ID, Carrier_ID);
//        scServiceXcnd49.delivery(W_ID, Carrier_ID);
        long end = System.currentTimeMillis();
        return "Done: " + (end - start);
    }

    @RequestMapping("/orderStatus")
    public String orderStatus(){
        int C_ID = 321;
        int W_ID = 3;
        int D_ID = 10;
        long start = System.currentTimeMillis();
        scServiceXcnd45.orderStatus(W_ID,D_ID,C_ID);
//        scServiceXcnd46.orderStatus(W_ID,D_ID,C_ID);
//        scServiceXcnd47.orderStatus(W_ID,D_ID,C_ID);
//        scServiceXcnd48.orderStatus(W_ID,D_ID,C_ID);
//        scServiceXcnd49.orderStatus(W_ID,D_ID,C_ID);
        long end = System.currentTimeMillis();
        return "Done: " + (end - start);
    }

    @RequestMapping("/stockLevel")
    public String stockLevel(){
        int W_ID = 3;
        int D_ID = 10;
        BigDecimal threshold = new BigDecimal("9.0");
        int numLastOrders = 1;
        long start = System.currentTimeMillis();
        scServiceXcnd45.stockLevel(W_ID, D_ID, threshold, numLastOrders);
//        scServiceXcnd46.stockLevel(W_ID, D_ID, threshold, numLastOrders);
//        scServiceXcnd47.stockLevel(W_ID, D_ID, threshold, numLastOrders);
//        scServiceXcnd48.stockLevel(W_ID, D_ID, threshold, numLastOrders);
//        scServiceXcnd49.stockLevel(W_ID, D_ID, threshold, numLastOrders);
        long end = System.currentTimeMillis();
        return "Done: " + (end - start);
    }

    @RequestMapping("/popularItem")
    public String popularItem(){
        int W_ID = 3;
        int D_ID = 10;
        int numLastOrders = 1;
        long start = System.currentTimeMillis();
        scServiceXcnd45.popularItem(W_ID, D_ID, numLastOrders);
//        scServiceXcnd46.popularItem(W_ID, D_ID, numLastOrders);
//        scServiceXcnd47.popularItem(W_ID, D_ID, numLastOrders);
//        scServiceXcnd48.popularItem(W_ID, D_ID, numLastOrders);
//        scServiceXcnd49.popularItem(W_ID, D_ID, numLastOrders);
        long end = System.currentTimeMillis();
        return "Done: " + (end - start);
    }

    @RequestMapping("/topBalance")
    public String topBalance(){
        long start = System.currentTimeMillis();
        scServiceXcnd45.topBalance();
//        scServiceXcnd46.topBalance();
//        scServiceXcnd47.topBalance();
//        scServiceXcnd48.topBalance();
//        scServiceXcnd49.topBalance();
        long end = System.currentTimeMillis();
        return "Done: " + (end - start);
    }

    @RequestMapping("/relatedCustomer")
    public String relatedCustomer(){
    	
    	int C_ID = 321;
        int W_ID = 3;
        int D_ID = 10;
        long start = System.currentTimeMillis();
        scServiceXcnd45.relatedCustomer(W_ID, D_ID, C_ID);
//        scServiceXcnd46.relatedCustomer(W_ID, D_ID, C_ID);
//        scServiceXcnd47.relatedCustomer(W_ID, D_ID, C_ID);
//        scServiceXcnd48.relatedCustomer(W_ID, D_ID, C_ID);
//        scServiceXcnd49.relatedCustomer(W_ID, D_ID, C_ID);
        long end = System.currentTimeMillis();

        return "Done: " + (end - start);
    }
}
