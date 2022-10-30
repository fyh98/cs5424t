package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import cs5424t.ysql.Entities.Xcnd45.OrderXcnd45;
import cs5424t.ysql.Entities.Xcnd49.OrderXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("orderRepositoryXcnd49")
public interface OrderRepositoryXcnd49 extends JpaRepository<OrderXcnd49, OrderPK> {
    OrderXcnd49 findTopByWarehouseIdAndDistrictIdAndCarrierIdIsNullOrderByIdAsc(Integer warehouseId, Integer districtId);

    OrderXcnd49 findTopByCustomerIdOrderByCreateTimeDesc(Integer customerId);

    OrderXcnd49 findTopByWarehouseIdAndDistrictIdAndCustomerIdOrderByIdDesc(Integer warehouseId, Integer districtId, Integer customerId);
    @Query("select max(id) from OrderXcnd49")
    Integer findMaxOId();

    @Query("select sum(numOfItems) from OrderXcnd49")
    Integer findSumOOlCnt();
}
