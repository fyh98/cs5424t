package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.Xcnd45.OrderXcnd45;
import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderRepositoryXcnd45")
public interface OrderRepositoryXcnd45 extends JpaRepository<OrderXcnd45, OrderPK> {
    OrderXcnd45 findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(Integer warehouseId, Integer districtId);

    OrderXcnd45 findTopByCustomerIdOrderByCreateTimeDesc(Integer customerId);
}
