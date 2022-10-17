package cs5424t.ysql.DAO;

import cs5424t.ysql.Entities.Order;
import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderPK> {
    Order findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(Integer warehouseId,Integer districtId);
    Order findTopByCustomerIdOrderByCreateTimeDesc(Integer customerId);
    Order findTopByWarehouseIdAndDistrictIdAndCarrierIdOrderByIdAsc(Integer warehouseId,Integer districtId, Integer carrierId);
}
