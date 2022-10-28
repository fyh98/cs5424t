package cs5424t.ysql.DAO;

import cs5424t.ysql.Entities.OrderLine;
import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLinePK> {

    @Query("select o from OrderLine o where o.warehouseId = ?1 and o.districtId = ?2 and o.orderId = ?3")
    List<OrderLine> findByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);

    List<OrderLine> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);
}
