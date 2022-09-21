package cs5424t.ysql.DAO;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, StockPK> {
    Stock findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);
}
