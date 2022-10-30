package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import cs5424t.ysql.Entities.Xcnd45.OrderXcnd45;
import cs5424t.ysql.Entities.Xcnd47.OrderXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("orderRepositoryXcnd47")
public interface OrderRepositoryXcnd47 extends JpaRepository<OrderXcnd47, OrderPK> {
    OrderXcnd47 findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(Integer warehouseId, Integer districtId);

    OrderXcnd47 findTopByCustomerIdOrderByCreateTimeDesc(Integer customerId);

    OrderXcnd47 findTopByWarehouseIdAndDistrictIdAndCustomerIdOrderByIdDesc(Integer warehouseId, Integer districtId, Integer customerId);
    @Query("select max(id) from OrderXcnd47")
    Integer findMaxOId();

    @Query("select sum(numOfItems) from OrderXcnd47")
    Integer findSumOOlCnt();
}
