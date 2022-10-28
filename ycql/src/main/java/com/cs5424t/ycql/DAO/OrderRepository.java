package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.Order;
import com.cs5424t.ycql.Entities.PrimaryKeys.OrderPK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CassandraRepository<Order, OrderPK> {
    //TODO : CHANGE SQL
    @Query("select * from order_ycql where o_w_id = ?0 and o_d_id = ?1 and o_carrier_id = -1 ORDER BY o_d_id, o_id asc limit 1;")
    Order findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(Integer warehouseId,Integer districtId);
    @Query("select * from order_ycql where o_w_id = ?0 and o_d_id = ?1 and o_id = ?2")
    Order findLastOrder(Integer warehouseId,Integer districtId, Integer orderId);
    @Query("select max(o_id) from order_ycql where o_w_id = ?0 and o_d_id = ?1 and o_c_id = ?2 group BY o_w_id,o_d_id,o_id ;")
    Integer findLastOrderId(Integer warehouseId,Integer districtId, Integer customerId);

    @Query("select max(o_id) from order_ycql;")
    Integer findMaxOId();

    @Query("select sum(o_ol_cnt) from order_ycql;")
    Integer findSumOOlCnt();
}
