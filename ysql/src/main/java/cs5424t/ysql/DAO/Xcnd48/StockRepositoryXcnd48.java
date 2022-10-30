package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd48.StockXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockRepositoryXcnd48")
public interface StockRepositoryXcnd48 extends JpaRepository<StockXcnd48, StockPK> {
    StockXcnd48 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);
}
