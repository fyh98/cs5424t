package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.Xcnd49.WarehouseXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("warehouseRepositoryXcnd49")
public interface WarehouseRepositoryXcnd49 extends JpaRepository<WarehouseXcnd49, Integer> {
}
