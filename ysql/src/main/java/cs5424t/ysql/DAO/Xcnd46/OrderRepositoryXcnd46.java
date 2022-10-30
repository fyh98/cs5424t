package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import cs5424t.ysql.Entities.Xcnd46.OrderXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderRepositoryXcnd46")
public interface OrderRepositoryXcnd46 extends JpaRepository<OrderXcnd46, OrderPK> {
    OrderXcnd46 findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(Integer warehouseId, Integer districtId);

    OrderXcnd46 findTopByCustomerIdOrderByCreateTimeDesc(Integer customerId);
}
