package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import cs5424t.ysql.Entities.Xcnd48.OrderLineXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository("orderLineRepositoryXcnd48")
public interface OrderLineRepositoryXcnd48 extends JpaRepository<OrderLineXcnd48, OrderLinePK> {

    @Query("select o from OrderLineXcnd48 o where o.warehouseId = ?1 and o.districtId = ?2 and o.orderId = ?3")
    List<OrderLineXcnd48> findAllByWarehouseIdAndDistrictIdAndOrderId(Integer warehouseId, Integer districtId, Integer orderId);

    @Query("select sum(totalPrice) from OrderLineXcnd48")
    BigDecimal findSumOlAmount();

    @Query("select sum(quantity) from OrderLineXcnd48")
    BigDecimal findSumOlQuantity();
}
