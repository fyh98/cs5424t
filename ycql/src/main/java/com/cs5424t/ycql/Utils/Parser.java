package com.cs5424t.ycql.Utils;

import com.cs5424t.ycql.Transactions.SupplyChainTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class Parser
{
    @Autowired
    private SupplyChainTransaction S;
    public void loadClientTran(String filePath)
    {
        File infile = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(infile))){

            while (reader.ready()){
                String[] trans = reader.readLine().split(",");
                switch (trans[0]) {
                    case "P" -> {
                        BigDecimal bd = new BigDecimal(trans[4]);
                        S.payment(Integer.parseInt(trans[1]), Integer.parseInt(trans[2]), Integer.parseInt(trans[3]), bd);
                    }
                    case "T" -> S.topBalance();
                    case "I" ->
                            S.popularItem(Integer.parseInt(trans[1]), Integer.parseInt(trans[2]), Integer.parseInt(trans[3]));
                    case "O" ->
                            S.orderStatus(Integer.parseInt(trans[1]), Integer.parseInt(trans[2]), Integer.parseInt(trans[3]));
                    case "D" -> S.delivery(Integer.parseInt(trans[1]), Integer.parseInt(trans[2]));
                    case "R" ->
                            S.relatedCustomer(Integer.parseInt(trans[1]), Integer.parseInt(trans[2]), Integer.parseInt(trans[3]));
                    case "S" -> {
                        BigDecimal bd = new BigDecimal(trans[3]);
                        S.stockLevel(Integer.parseInt(trans[1]), Integer.parseInt(trans[2]), bd, Integer.parseInt(trans[4]));
                    }
                    case "N" -> {
                        List<Integer> itemN = null, supplier = null, quan = null;
                        for (int i = 1; i <= Integer.parseInt(trans[4]); i++) {
                            String[] item = reader.readLine().split(",");
                            itemN.add(Integer.parseInt(item[0]));
                            supplier.add(Integer.parseInt(item[1]));
                            quan.add(Integer.parseInt(item[2]));
                        }
                        S.newOrder(Integer.parseInt(trans[1]), Integer.parseInt(trans[2]), Integer.parseInt(trans[3]), Integer.parseInt(trans[4]), itemN, supplier, quan);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
