package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd49.StockXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockRepositoryXcnd49")
public interface StockRepositoryXcnd49 extends JpaRepository<StockXcnd49, StockPK> {
    StockXcnd49 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);
}
