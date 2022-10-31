package com.cs5424t.ycql.Utils;

import java.io.*;
import java.util.*;

public class OrderCustItemsCSV {
    public static void main( String[] args ) {

        Map<String, List<Object>> map = new HashMap<>();

        String csvFile = "D:\\Courses\\CS5424 Distributed Database\\project" +
                            "\\project_files\\data_files\\order.csv";
        String line;
        String cvsSplitBy = ",";
        int cnt = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] curRow = line.split(cvsSplitBy);
                String warehouseId = curRow[0];
                String districtId = curRow[1];
                String orderId = curRow[2];
                String customerId = curRow[3];

                String key = warehouseId + " " + districtId + " " + orderId;
                map.put(key, new ArrayList<>());

                List<Object> tmpList = map.get(key);

                tmpList.add(warehouseId);
                tmpList.add(districtId);
                tmpList.add(orderId);
                tmpList.add(customerId);
                tmpList.add(new HashSet<String>());
                cnt++;

                if(cnt % 10000 == 0) {
                    System.out.println(cnt / 10000);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // order line

        csvFile = "D:\\Courses\\CS5424 Distributed Database\\project" +
                "\\project_files\\data_files\\order-line.csv";

        cnt = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] curRow = line.split(cvsSplitBy);
                String warehouseId = curRow[0];
                String districtId = curRow[1];
                String orderId = curRow[2];
                String itemId = curRow[4];

                String key = warehouseId + " " + districtId + " " + orderId;

                HashSet<String> itemSet = (HashSet<String>) map.get(key).get(4);
                itemSet.add(itemId);

                cnt++;

                if(cnt % 100000 == 0) {
                    System.out.println(cnt / 100000);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        save(map);
    }

    public static void save(Map<String, List<Object>> map) {

        File csv = new File("D:\\Courses\\CS5424 Distributed Database\\project" +
                "\\project_files\\data_files\\order-cust-items.csv");

        try{
            BufferedWriter writeText = new BufferedWriter(new FileWriter(csv));

            for(String key : map.keySet()){
                writeText.newLine();

                List<Object> objects = map.get(key);
                StringBuilder toWrite = new StringBuilder();
                toWrite.append(objects.get(0));
                toWrite.append(",");
                toWrite.append(objects.get(1));
                toWrite.append(",");
                toWrite.append(objects.get(2));
                toWrite.append(",");
                toWrite.append(objects.get(3));
                toWrite.append(",");

                HashSet<String> set = (HashSet<String>) objects.get(4);

                StringBuilder items = new StringBuilder("\"{");
                for(String item : set){
                    items.append(item).append(",");
                }
                items.replace(items.length()-1, items.length(), "}\"");

                toWrite.append(items);

                writeText.write(toWrite.toString());
            }

            writeText.flush();
            writeText.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
