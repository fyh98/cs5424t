package cs5424t.ysql.Utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BenchMarkStatistics {
    private long duration = 0L;

    private int tranxNum = 0;

    private List<Long> durationList = new ArrayList<>();

    public BenchMarkStatistics(long duration, int tranxNum, List<Long> durationList) {
        this.duration = duration;
        this.tranxNum = tranxNum;
        this.durationList = durationList;
    }
}
