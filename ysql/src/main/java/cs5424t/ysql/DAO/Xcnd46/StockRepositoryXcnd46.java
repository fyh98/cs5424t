package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd46.StockXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockRepositoryXcnd46")
public interface StockRepositoryXcnd46 extends JpaRepository<StockXcnd46, StockPK> {
    StockXcnd46 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);
}
