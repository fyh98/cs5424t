package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd45.StockXcnd45;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stockRepositoryXcnd45")
public interface StockRepositoryXcnd45 extends JpaRepository<StockXcnd45, StockPK> {
    StockXcnd45 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);
}
