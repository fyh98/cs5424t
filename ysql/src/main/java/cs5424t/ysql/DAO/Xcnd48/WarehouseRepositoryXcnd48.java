package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.Xcnd48.WarehouseXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("warehouseRepositoryXcnd48")
public interface WarehouseRepositoryXcnd48 extends JpaRepository<WarehouseXcnd48, Integer> {
    @Query("select sum(ytd) from WarehouseXcnd48")
    BigDecimal findSumWYtd();
}
