package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.OrderLine;
import com.cs5424t.ycql.Entities.PrimaryKeys.OrderLinePK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends CassandraRepository<OrderLine, OrderLinePK> {
    @Query("select * from order_line_ycql  where ol_w_id = ?0 and ol_d_id = ?1 and ol_o_id = ?2")
    List<OrderLine> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);
}
