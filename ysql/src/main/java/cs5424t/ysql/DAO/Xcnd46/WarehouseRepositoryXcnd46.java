package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.Xcnd46.WarehouseXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("warehouseRepositoryXcnd46")
public interface WarehouseRepositoryXcnd46 extends JpaRepository<WarehouseXcnd46, Integer> {

    @Query("select sum(ytd) from WarehouseXcnd46")
    BigDecimal findSumWYtd();
}
