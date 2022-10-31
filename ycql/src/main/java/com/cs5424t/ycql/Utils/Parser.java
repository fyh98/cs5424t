package com.cs5424t.ycql.Utils;

import com.cs5424t.ycql.Transactions.SupplyChainTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Parser {
    @Autowired
    private SupplyChainTransaction scService;

    private long duration = 0L;

    private int tranxNum = 0;

    private List<Long> durationList = new ArrayList<>();

    public void loadClientTran(String filePath) {
        File infile = new File(filePath);
        long start = System.currentTimeMillis();
        try (BufferedReader reader = new BufferedReader(new FileReader(infile))){

            while (reader.ready()){
                String[] trans = reader.readLine().split(",");
                long curStart = System.currentTimeMillis();
                switch (trans[0]) {
                    case "P" : {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        int customerId = Integer.parseInt(trans[3]);
                        BigDecimal bd = new BigDecimal(trans[4]);

                        System.out.println("payment " + warehouseId + " " + districtId +
                                " " + customerId + " " + bd);

                        scService.payment(warehouseId, districtId, customerId, bd);
                        break;
                    }
                    case "T" : {
                        System.out.println("topBalance");

                        scService.topBalance();
                        break;
                    }
                    case "I" : {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        int numLastOrders = Integer.parseInt(trans[3]);

                        System.out.println("popularItem " + warehouseId + " " + districtId +
                                " " + numLastOrders);

                        scService.popularItem(warehouseId, districtId, numLastOrders);
                        break;
                    }

                    case "O" : {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        int customerId = Integer.parseInt(trans[3]);

                        System.out.println("orderStatus " + warehouseId + " " + districtId +
                                " " + customerId);

                        scService.orderStatus(warehouseId, districtId, customerId);
                        break;
                    }
                    case "D" : {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int carrierId = Integer.parseInt(trans[2]);

                        System.out.println("delivery " + warehouseId + " " + carrierId);

                        scService.delivery(warehouseId, carrierId);
                        break;
                    }
                    case "R" : {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        int customerId = Integer.parseInt(trans[3]);

                        System.out.println("relatedCustomer " + warehouseId + " " + districtId
                                + " " + customerId);

                        scService.relatedCustomer(warehouseId, districtId, customerId);
                        break;
                    }
                    case "S" : {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        BigDecimal bd = new BigDecimal(trans[3]);
                        int numLastOrders = Integer.parseInt(trans[4]);

                        System.out.println("stockLevel " + warehouseId + " " + districtId
                                + " " + bd + " " + numLastOrders);

                        scService.stockLevel(warehouseId, districtId, bd, numLastOrders);
                        break;
                    }
                    case "N" : {
                        List<Integer> itemN = new ArrayList<>();
                        List<Integer> supplier = new ArrayList<>();
                        List<Integer> quan = new ArrayList<>();
                        int customerId = Integer.parseInt(trans[1]);
                        int warehouseId = Integer.parseInt(trans[2]);
                        int districtId = Integer.parseInt(trans[3]);
                        int itemTotalNum = Integer.parseInt(trans[4]);

                        for (int i = 1; i <= itemTotalNum; i++) {
                            String[] item = reader.readLine().split(",");
                            itemN.add(Integer.parseInt(item[0]));
                            supplier.add(Integer.parseInt(item[1]));
                            quan.add(Integer.parseInt(item[2]));
                        }

                        System.out.println("newOrder " + warehouseId + " " + districtId
                                + " " + customerId + " " + itemTotalNum);

                        scService.newOrder(warehouseId, districtId, customerId, itemTotalNum,
                                itemN, supplier, quan);
                        break;
                    }
                }
                long curEnd = System.currentTimeMillis();
                durationList.add(curEnd - curStart);
                tranxNum++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        Collections.sort(durationList);
        duration = end - start;
    }

    public void printStatics(){
        //todo
    }

}
