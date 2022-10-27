package com.cs5424t.ycql.Controller;

import com.cs5424t.ycql.Transactions.SupplyChainTransaction;
import com.cs5424t.ycql.Utils.Parser;
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
    private Parser p;

    @Autowired
    SupplyChainTransaction scService;

    @RequestMapping("/newOrder")
    public String newOrder(){
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

        scService.newOrder(W_ID, D_ID, C_ID, M, itemNumber, supplier, quantity);

        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }

    @RequestMapping("/payment")
    public String payment(){
        int C_ID = 321;
        int W_ID = 3;
        int D_ID = 10;
        BigDecimal paymentAmount = new BigDecimal("100000");
        long start = System.currentTimeMillis();
        scService.payment(W_ID,D_ID,C_ID,paymentAmount);
        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }

    @RequestMapping("/delivery")
    public String delivery(){
        int W_ID = 3;
        int Carrier_ID = 10;

        long start = System.currentTimeMillis();

        scService.delivery(W_ID,Carrier_ID);

        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }

    @RequestMapping("/orderStatus")
    public String orderStatus(){
        int C_ID = 321;
        int W_ID = 3;
        int D_ID = 10;
        long start = System.currentTimeMillis();
        scService.orderStatus(W_ID,D_ID,C_ID);
        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }

    @RequestMapping("/stockLevel")
    public String stockLevel(){
        int W_ID = 3;
        int D_ID = 10;
        BigDecimal threshold = new BigDecimal("9.0");
        int numLastOrders = 1;
        long start = System.currentTimeMillis();
        scService.stockLevel(W_ID, D_ID, threshold, numLastOrders);
        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }

    @RequestMapping("/popularItem")
    public String popularItem(){
        int W_ID = 3;
        int D_ID = 10;
        int numLastOrders = 1;
        long start = System.currentTimeMillis();
        scService.popularItem(W_ID, D_ID, numLastOrders);
        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }
    @RequestMapping("/topBalance")
    public String topBalance(){
        long start = System.currentTimeMillis();
        scService.topBalance();
        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }
    @RequestMapping("/relatedCustomer")
    public String relatedCustomer(){
    	int W_ID = 3;
        int D_ID = 10;
        int numLastOrders = 1;
        long start = System.currentTimeMillis();
        scService.relatedCustomer(W_ID, D_ID, numLastOrders);
        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }

    @RequestMapping("/benchmark")
    public String benchmark(){
        long start = System.currentTimeMillis();

        p.loadClientTran("D:\\Courses\\CS5424 Distributed Database\\project\\project_files\\xact_files\\test.txt");

        long end = System.currentTimeMillis();

        double duration = (end - start) * 1.0 / 1000;
        return "Total duration: " + duration + " seconds";
    }
}
