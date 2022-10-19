package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.OrderLine;
import com.cs5424t.ycql.Entities.PrimaryKeys.OrderLinePK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends CassandraRepository<OrderLine, OrderLinePK> {
    @Query("select o from OrderLine o where o.warehouseId = ?1 and o.districtId = ?2 and o.orderId = ?3")
    List<OrderLine> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);
}
