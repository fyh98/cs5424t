package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.Xcnd45.OrderLineXcnd45;
import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderLineRepositoryXcnd45")
public interface OrderLineRepositoryXcnd45 extends JpaRepository<OrderLineXcnd45, OrderLinePK> {

    @Query("select o from OrderLineXcnd45 o where o.warehouseId = ?1 and o.districtId = ?2 and o.orderId = ?3")
    List<OrderLineXcnd45> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);
}
