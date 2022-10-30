package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import cs5424t.ysql.Entities.Xcnd48.OrderXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderRepositoryXcnd48")
public interface OrderRepositoryXcnd48 extends JpaRepository<OrderXcnd48, OrderPK> {
    OrderXcnd48 findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(Integer warehouseId, Integer districtId);

    OrderXcnd48 findTopByCustomerIdOrderByCreateTimeDesc(Integer customerId);
}
