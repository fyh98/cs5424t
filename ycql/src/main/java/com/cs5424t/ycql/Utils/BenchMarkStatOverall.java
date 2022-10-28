package com.cs5424t.ycql.Utils;

import lombok.Data;

import java.util.List;

@Data
public class BenchMarkStatOverall {

    private List<BenchMarkStatistics> data;

    public BenchMarkStatOverall(List<BenchMarkStatistics> data) {
        this.data = data;
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
    public void saveStalenessInfo(){
        //todo
    }
}
