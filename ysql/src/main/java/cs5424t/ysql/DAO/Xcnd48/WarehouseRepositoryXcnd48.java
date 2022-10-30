package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.Xcnd48.WarehouseXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("warehouseRepositoryXcnd48")
public interface WarehouseRepositoryXcnd48 extends JpaRepository<WarehouseXcnd48, Integer> {
}
