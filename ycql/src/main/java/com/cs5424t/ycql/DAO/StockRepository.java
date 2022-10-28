package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.PrimaryKeys.StockPK;
import com.cs5424t.ycql.Entities.Stock;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface StockRepository extends CassandraRepository<Stock, StockPK> {

    @Query("select sum(s_quantity) from stock_ycql ")
    BigDecimal findSumSQuantity();

    @Query("select sum(s_ytd) from stock_ycql ")
    BigDecimal findSumSYtd();

    @Query("select sum(s_order_cnt) from stock_ycql ")
    Integer findSumSOrderCnt();

    @Query("select sum(s_remote_cnt) from stock_ycql ")
    Integer findSumSRemoteCnt();
}
