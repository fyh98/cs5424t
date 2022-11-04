package cs5424t.ysql.Utils;


import com.csvreader.CsvWriter;
import cs5424t.ysql.Transactions.SupplyChainTransaction;
import lombok.Data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        this.saveClientReport();
        this.saveThroughputInfo();
        this.saveStalenessInfo();
    }

    // part-1
    public void saveClientReport(){
        CsvWriter csv = new CsvWriter(this.filePath + "\\client_ysql.csv", ',', StandardCharsets.UTF_8);
        for(int i = 0; i < this.data.size(); i++){
            try {
                List<String> client = new ArrayList<>();
                client.add(String.valueOf(i));
                int tranxNum = this.data.get(i).getTranxNum();
                long duration = this.data.get(i).getDuration();
                List<Long> durationList = this.data.get(i).getDurationList();
                client.add(String.valueOf(tranxNum));
                client.add(String.valueOf(duration));
                client.add(String.valueOf(tranxNum * 1.0 / duration));
                client.add(String.valueOf(duration * 1.0 /tranxNum));
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

            throughputs.add(1000 * xactCount / time);
        }

        String[] min_throughput = {Float.toString(Collections.min(throughputs))};
        String[] max_throughput = {Float.toString(Collections.max(throughputs))};
        float sum = 0;
        for (float throughput : throughputs) {
            sum += throughput;
        }
        String[] avg_throughput = {Float.toString(sum / throughputs.size())};

        CsvWriter csvWriter = new CsvWriter(this.filePath + "\\throughput_ysql.csv", ',', Charset.forName("UTF-8"));
        try {
            csvWriter.writeRecord(min_throughput);
            csvWriter.writeRecord(max_throughput);
            csvWriter.writeRecord(avg_throughput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        csvWriter.close();
    }

    // part-3
    public void saveStalenessInfo()  {
        List<Object> measurements = scService.measurePerformance();

        CsvWriter csvWriter = new CsvWriter(this.filePath + "\\dbstate_ysql.csv", ',', Charset.forName("UTF-8"));
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
