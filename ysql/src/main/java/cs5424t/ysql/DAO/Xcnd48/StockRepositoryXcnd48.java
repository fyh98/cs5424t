package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd48.StockXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("stockRepositoryXcnd48")
public interface StockRepositoryXcnd48 extends JpaRepository<StockXcnd48, StockPK> {
    StockXcnd48 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);

    @Query("select sum(stockNum) from StockXcnd48")
    BigDecimal findSumSQuantity();

    @Query("select sum(ytd) from StockXcnd48")
    BigDecimal findSumSYtd();

    @Query("select sum(numOfOrder) from StockXcnd48")
    Integer findSumSOrderCnt();

    @Query("select sum(numOfRemoteOrder) from StockXcnd48")
    Integer findSumSRemoteCnt();
}
