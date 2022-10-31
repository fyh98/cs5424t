package com.cs5424t.ycql.Controller;

import com.cs5424t.ycql.DAO.OrderCustItemRepository;
import com.cs5424t.ycql.Entities.OrderCustItem;
import com.cs5424t.ycql.Entities.PrimaryKeys.OrderCustItemPK;
import com.cs5424t.ycql.Transactions.SupplyChainTransaction;
import com.cs5424t.ycql.Utils.BenchMarkStatOverall;
import com.cs5424t.ycql.Utils.BenchMarkStatistics;
import com.cs5424t.ycql.Utils.BenchmarkThread;
import com.cs5424t.ycql.Utils.Parser;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@RestController
@RequestMapping("/sc")
public class SupplyChainController {

    @Autowired
    Parser p;

    @Autowired
    SupplyChainTransaction scService;

    @Autowired
    OrderCustItemRepository orderCustItemRepository;

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
        scService.payment(W_ID, D_ID, C_ID, paymentAmount);
        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }

    @RequestMapping("/delivery")
    public String delivery(){
        int W_ID = 1;
        int Carrier_ID = 1;

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
        scService.orderStatus(W_ID, D_ID, C_ID);
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
//        scService.relatedCustomer(10, 10, 2819);
//        scService.relatedCustomer(10, 6, 2162);
        long end = System.currentTimeMillis();
        return "Success! " + (end - start);
    }

    @RequestMapping("/benchmarkTest")
    public String benchmarkTest(){
        long start = System.currentTimeMillis();

        p.loadClientTran("D:\\Courses\\CS5424 Distributed Database\\project\\project_files\\xact_files_test\\19.txt");

        long end = System.currentTimeMillis();

        double duration = (end - start) * 1.0 / 1000;
        return "Total duration: " + duration + " seconds";
    }

    @RequestMapping("/benchmark")
    public String benchmark() throws InterruptedException, ExecutionException {
        String locationFolder = "D:\\Courses\\CS5424 Distributed Database\\project\\project_files\\xact_files" +
                "\\";

        int totalTxtNum = 20;

        List<Thread> threadList = new ArrayList<>();
        List<FutureTask<BenchMarkStatistics>> futureList = new ArrayList<>();

        long start = System.currentTimeMillis();

        for(int i=0;i<totalTxtNum;i++){
            FutureTask<BenchMarkStatistics> future = new FutureTask<>
                                        (new BenchmarkThread(i, locationFolder, scService));
            threadList.add(new Thread(future));
            futureList.add(future);
        }

        for(int i=0;i<totalTxtNum;i++){
            threadList.get(i).start();
        }

        for(int i=0;i<totalTxtNum;i++){
            threadList.get(i).join();
        }

        List<BenchMarkStatistics> results = new ArrayList<>();

        for(int i=0;i<totalTxtNum;i++){
            results.add(futureList.get(i).get());
        }

        BenchMarkStatOverall stat = new BenchMarkStatOverall(results, locationFolder, scService);

        for(BenchMarkStatistics tmp : results){
            System.out.println(tmp);
        }

        stat.saveResults();

        long end = System.currentTimeMillis();

        return "Done: " + (end - start);
    }

    @RequestMapping("/measure")
    public String measure(){
        List<Object> measurements = scService.measurePerformance();
        String filePath = "D:\\Courses\\CS5424 Distributed Database\\project\\ycql_not_reinitalized";

        CsvWriter csvWriter = new CsvWriter(filePath + "\\dbstate.csv", ',', Charset.forName("UTF-8"));
        for(Object measure : measurements){
            try {
                String[] record = new String[]{measure.toString()};
                csvWriter.writeRecord(record);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        csvWriter.close();
        return "success";
    }

    @RequestMapping("/test")
    public String test() {
        Optional<OrderCustItem> byId = orderCustItemRepository.findById(new OrderCustItemPK(10, 6, 193));
        return byId.get().toString();
    }
}
