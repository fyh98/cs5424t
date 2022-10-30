package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.Xcnd49.WarehouseXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("warehouseRepositoryXcnd49")
public interface WarehouseRepositoryXcnd49 extends JpaRepository<WarehouseXcnd49, Integer> {
    @Query("select sum(ytd) from WarehouseXcnd49")
    BigDecimal findSumWYtd();
}
