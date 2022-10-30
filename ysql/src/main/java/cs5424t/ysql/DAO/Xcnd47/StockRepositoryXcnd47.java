package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd47.StockXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("stockRepositoryXcnd47")
public interface StockRepositoryXcnd47 extends JpaRepository<StockXcnd47, StockPK> {
    StockXcnd47 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);

    @Query("select sum(stockNum) from StockXcnd47")
    BigDecimal findSumSQuantity();

    @Query("select sum(ytd) from StockXcnd47")
    BigDecimal findSumSYtd();

    @Query("select sum(numOfOrder) from StockXcnd47")
    Integer findSumSOrderCnt();

    @Query("select sum(numOfRemoteOrder) from StockXcnd47")
    Integer findSumSRemoteCnt();
}
