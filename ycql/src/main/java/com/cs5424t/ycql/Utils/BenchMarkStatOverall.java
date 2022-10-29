package com.cs5424t.ycql.Utils;

import com.cs5424t.ycql.Transactions.SupplyChainTransaction;
import com.csvreader.CsvWriter;
import com.csvreader.CsvReader;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BenchMarkStatOverall {

    SupplyChainTransaction scService;
    private List<BenchMarkStatistics> data;
    private String filePath;

    public BenchMarkStatOverall(List<BenchMarkStatistics> data, String filePath,
                                SupplyChainTransaction scService) {
        this.data = data;
        this.filePath = filePath;
        this.scService = scService;
    }

    public BenchMarkStatOverall() {

    }

    public void saveResults(){
        System.out.println(data.get(0));
        System.out.println(data.get(1));
        this.saveClientReport();
        this.saveThroughputInfo();
        this.saveStalenessInfo();
    }

    // part-1
    public void saveClientReport(){
        CsvWriter csv = new CsvWriter(this.filePath + "\\client.csv", ',', StandardCharsets.UTF_8);
        for(int i = 0; i < this.data.size(); i++){
            try {
                List<String> client = new ArrayList<>();
                client.add(String.valueOf(i));
                int tranxNum = this.data.get(i).getTranxNum();
                long duration = this.data.get(i).getDuration();
                List<Long> durationList = this.data.get(i).getDurationList();
                client.add(String.valueOf(tranxNum));
                client.add(String.valueOf(duration));
                client.add(String.valueOf(tranxNum/duration));
                client.add(String.valueOf(duration/tranxNum));
                client.add(String.valueOf(durationList.get((int) Math.floor(tranxNum*0.5))));
                client.add(String.valueOf(durationList.get((int) Math.floor(tranxNum*0.95))));
                client.add(String.valueOf(durationList.get((int) Math.floor(tranxNum*0.99))));

                String[] c = client.toArray(new String[client.size()]);
                csv.writeRecord(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        csv.close();
    }

    // part-2
    public void saveThroughputInfo() {
        ArrayList<Float> throughputs = new ArrayList<>();
        for (int i=0; i < this.data.size(); i++) {
            BenchMarkStatistics benchMarkStatistics = this.data.get(i);
            float xactCount = (float) benchMarkStatistics.getTranxNum();
            float time = (float) benchMarkStatistics.getDuration();

            throughputs.add(xactCount / time);
        }

        float min_throughput = Collections.min(throughputs);
        float max_throughput = Collections.max(throughputs);
        float sum = 0;
        for (float throughput : throughputs) {
            sum += throughput;
        }
        float avg_throughput = (Float) sum / throughputs.size();

        CsvWriter csvWriter = new CsvWriter(this.filePath + "\\throughput.csv", ',', Charset.forName("UTF-8"));
        String[] header = {"min throughput", "max throughput", "avg throughput"};
        String[] data = {Float.toString(min_throughput), Float.toString(max_throughput), Float.toString(avg_throughput)};
        try {
            csvWriter.writeRecord(header);
            csvWriter.writeRecord(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // part-3
    public void saveStalenessInfo()  {
        List<Object> measurements = scService.measurePerformance();

        CsvWriter csvWriter = new CsvWriter(this.filePath + "\\dbstate.csv", ',', Charset.forName("UTF-8"));
        for(Object measure : measurements){
            try {
                String[] record = new String[]{measure.toString()};
                csvWriter.writeRecord(record);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        csvWriter.close();
    }
}
