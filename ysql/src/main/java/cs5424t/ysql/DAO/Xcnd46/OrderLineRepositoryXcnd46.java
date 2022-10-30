package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import cs5424t.ysql.Entities.Xcnd46.OrderLineXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository("orderLineRepositoryXcnd46")
public interface OrderLineRepositoryXcnd46 extends JpaRepository<OrderLineXcnd46, OrderLinePK> {

    @Query("select o from OrderLineXcnd46 o where o.warehouseId = ?1 and o.districtId = ?2 and o.orderId = ?3")
    List<OrderLineXcnd46> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);

    @Query("select sum(totalPrice) from OrderLineXcnd46")
    BigDecimal findSumOlAmount();

    @Query("select sum(quantity) from OrderLineXcnd46")
    BigDecimal findSumOlQuantity();
}
