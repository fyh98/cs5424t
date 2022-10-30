package cs5424t.ysql.Utils;

import cs5424t.ysql.Transactions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class Parser
{

    @Autowired
    private SupplyChainTransaction45 S;
    public void loadClientTran(String filePath)
    {
        File infile = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(infile))){

            while (reader.ready()){
                String[] trans = reader.readLine().split(",");
                switch (trans[0]) {
                    case "P" -> {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        int customerId = Integer.parseInt(trans[3]);
                        BigDecimal bd = new BigDecimal(trans[4]);

                        System.out.println("payment " + warehouseId + " " + districtId +
                                " " + customerId + " " + bd);

                        S.payment(warehouseId, districtId, customerId, bd);
                    }
                    case "T" -> {
                        System.out.println("topBalance");

                        S.topBalance();
                    }
                    case "I" ->{
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        int numLastOrders = Integer.parseInt(trans[3]);

                        System.out.println("popularItem " + warehouseId + " " + districtId +
                                " " + numLastOrders);

                        S.popularItem(warehouseId, districtId, numLastOrders);
                    }
                    case "O" -> {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        int customerId = Integer.parseInt(trans[3]);

                        System.out.println("orderStatus " + warehouseId + " " + districtId +
                                " " + customerId);

                        S.orderStatus(warehouseId, districtId, customerId);
                    }
                    case "D" -> {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int carrierId = Integer.parseInt(trans[2]);

                        System.out.println("delivery " + warehouseId + " " + carrierId);

                        S.delivery(warehouseId, carrierId);
                    }
                    case "R" -> {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        int customerId = Integer.parseInt(trans[3]);

                        System.out.println("relatedCustomer " + warehouseId + " " + districtId
                                + " " + customerId);

                        S.relatedCustomer(warehouseId, districtId, customerId);
                    }
                    case "S" -> {
                        int warehouseId = Integer.parseInt(trans[1]);
                        int districtId = Integer.parseInt(trans[2]);
                        BigDecimal bd = new BigDecimal(trans[3]);
                        int numLastOrders = Integer.parseInt(trans[4]);

                        System.out.println("stockLevel " + warehouseId + " " + districtId
                                + " " + bd + " " + numLastOrders);

                        S.stockLevel(warehouseId, districtId, bd, numLastOrders);
                    }
                    case "N" -> {
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

                        S.newOrder(warehouseId, districtId, customerId, itemTotalNum,
                                itemN, supplier, quan);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
