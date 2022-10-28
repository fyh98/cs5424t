package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.PrimaryKeys.WarehousePK;
import com.cs5424t.ycql.Entities.Warehouse;


import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WarehouseRepository extends CassandraRepository<Warehouse, WarehousePK> {
	@Query("select sum(w_ytd) from warehouse_ycql;")
    BigDecimal findSumWYtd();
}
