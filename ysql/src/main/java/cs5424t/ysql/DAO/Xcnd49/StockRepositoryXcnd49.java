package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd49.StockXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("stockRepositoryXcnd49")
public interface StockRepositoryXcnd49 extends JpaRepository<StockXcnd49, StockPK> {
    StockXcnd49 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);

    @Query("select sum(stockNum) from StockXcnd49")
    BigDecimal findSumSQuantity();

    @Query("select sum(ytd) from StockXcnd49")
    BigDecimal findSumSYtd();

    @Query("select sum(numOfOrder) from StockXcnd49")
    Integer findSumSOrderCnt();

    @Query("select sum(numOfRemoteOrder) from StockXcnd49")
    Integer findSumSRemoteCnt();
}
