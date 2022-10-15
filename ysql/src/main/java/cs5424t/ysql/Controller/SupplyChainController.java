package cs5424t.ysql.Controller;

import cs5424t.ysql.Transactions.SupplyChainTransaction;
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

        scService.newOrder(W_ID, D_ID, C_ID, M, itemNumber, supplier, quantity);

        return "Success!";
    }

    @RequestMapping("/payment")
    public String payment(){
        int C_ID = 321;
        int W_ID = 3;
        int D_ID = 10;
        BigDecimal paymentAmount = new BigDecimal("100000");

        scService.payment(W_ID,D_ID,C_ID,paymentAmount);
        return "Success!";
    }

    @RequestMapping("/delivery")
    public String delivery(){
        int W_ID = 3;
        int Carrier_ID = 10;

        scService.delivery(W_ID,Carrier_ID);
        return "Success!";
    }

    @RequestMapping("/orderStatus")
    public String orderStatus(){
        int C_ID = 321;
        int W_ID = 3;
        int D_ID = 10;

        scService.orderStatus(W_ID,D_ID,C_ID);
        return "Success!";
    }



}
