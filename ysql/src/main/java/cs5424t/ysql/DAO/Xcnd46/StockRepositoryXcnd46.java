package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import cs5424t.ysql.Entities.Xcnd46.StockXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("stockRepositoryXcnd46")
public interface StockRepositoryXcnd46 extends JpaRepository<StockXcnd46, StockPK> {
    StockXcnd46 findByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);

    @Query("select sum(stockNum) from StockXcnd46")
    BigDecimal findSumSQuantity();

    @Query("select sum(ytd) from StockXcnd46")
    BigDecimal findSumSYtd();

    @Query("select sum(numOfOrder) from StockXcnd46")
    Integer findSumSOrderCnt();

    @Query("select sum(numOfRemoteOrder) from StockXcnd46")
    Integer findSumSRemoteCnt();
}
