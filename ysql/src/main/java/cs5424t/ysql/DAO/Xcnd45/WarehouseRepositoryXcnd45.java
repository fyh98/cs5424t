package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.Xcnd45.WarehouseXcnd45;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("warehouseRepositoryXcnd45")
public interface WarehouseRepositoryXcnd45 extends JpaRepository<WarehouseXcnd45, Integer> {

    @Query("select sum(ytd) from WarehouseXcnd45")
    BigDecimal findSumWYtd();

}
