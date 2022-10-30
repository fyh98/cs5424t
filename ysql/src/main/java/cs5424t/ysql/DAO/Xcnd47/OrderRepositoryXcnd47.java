package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import cs5424t.ysql.Entities.Xcnd47.OrderXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderRepositoryXcnd47")
public interface OrderRepositoryXcnd47 extends JpaRepository<OrderXcnd47, OrderPK> {
    OrderXcnd47 findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(Integer warehouseId, Integer districtId);

    OrderXcnd47 findTopByCustomerIdOrderByCreateTimeDesc(Integer customerId);
}
