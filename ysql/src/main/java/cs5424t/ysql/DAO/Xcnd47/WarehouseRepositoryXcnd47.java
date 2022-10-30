package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.Xcnd47.WarehouseXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("warehouseRepositoryXcnd47")
public interface WarehouseRepositoryXcnd47 extends JpaRepository<WarehouseXcnd47, Integer> {
    @Query("select sum(ytd) from WarehouseXcnd47")
    BigDecimal findSumWYtd();
}
