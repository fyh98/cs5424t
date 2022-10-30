package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.Xcnd46.WarehouseXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("warehouseRepositoryXcnd46")
public interface WarehouseRepositoryXcnd46 extends JpaRepository<WarehouseXcnd46, Integer> {
}
