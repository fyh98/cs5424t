package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import cs5424t.ysql.Entities.Xcnd47.OrderLineXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderLineRepositoryXcnd47")
public interface OrderLineRepositoryXcnd47 extends JpaRepository<OrderLineXcnd47, OrderLinePK> {

    @Query("select o from OrderLineXcnd47 o where o.warehouseId = ?1 and o.districtId = ?2 and o.orderId = ?3")
    List<OrderLineXcnd47> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);
}
