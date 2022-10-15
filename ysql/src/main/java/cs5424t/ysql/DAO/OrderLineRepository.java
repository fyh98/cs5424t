package cs5424t.ysql.DAO;

import cs5424t.ysql.Entities.OrderLine;
import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLinePK> {
    List<OrderLine> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);
}
