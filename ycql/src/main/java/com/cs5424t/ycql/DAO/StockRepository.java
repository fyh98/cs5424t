package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.PrimaryKeys.StockPK;
import com.cs5424t.ycql.Entities.Stock;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CassandraRepository<Stock, StockPK> {

}
