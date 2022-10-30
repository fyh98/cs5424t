package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd45.StockXcnd45;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("stockRepositoryXcnd45")
public interface StockRepositoryXcnd45 extends JpaRepository<StockXcnd45, StockPK> {
    StockXcnd45 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);

    @Query("select sum(stockNum) from StockXcnd45 ")
    BigDecimal findSumSQuantity();

    @Query("select sum(ytd) from StockXcnd45 ")
    BigDecimal findSumSYtd();

    @Query("select sum(numOfOrder) from StockXcnd45 ")
    Integer findSumSOrderCnt();

    @Query("select sum(numOfRemoteOrder) from StockXcnd45 ")
    Integer findSumSRemoteCnt();
}
