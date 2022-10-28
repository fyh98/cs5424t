package com.cs5424t.ycql.Utils;

import com.cs5424t.ycql.Transactions.SupplyChainTransaction;
import com.csvreader.CsvWriter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Data
public class BenchMarkStatOverall {

    @Autowired
    SupplyChainTransaction scService;

    private List<BenchMarkStatistics> data;
    private String filePath;

    public BenchMarkStatOverall(List<BenchMarkStatistics> data, String filePath) {
        this.data = data;
        this.filePath = filePath;
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
        //todo
    }

    // part-2
    public void saveThroughputInfo(){
        //todo
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
