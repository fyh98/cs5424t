package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import cs5424t.ysql.Entities.Xcnd49.OrderLineXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderLineRepositoryXcnd49")
public interface OrderLineRepositoryXcnd49 extends JpaRepository<OrderLineXcnd49, OrderLinePK> {

    @Query("select o from OrderLineXcnd49 o where o.warehouseId = ?1 and o.districtId = ?2 and o.orderId = ?3")
    List<OrderLineXcnd49> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);
}
