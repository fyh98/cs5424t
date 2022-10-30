package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.Xcnd48.CustomerXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("customerRepositoryXcnd48")
public interface CustomerRepositoryXcnd48 extends JpaRepository<CustomerXcnd48, CustomerPK> {
    @Query("select sum(balance) from CustomerXcnd48")
    BigDecimal findSumCBalance();

    @Query("select sum(ytdPayment) from CustomerXcnd48")
    Float findSumCYtdPayment();

    @Query("select sum(numOfPayment) from CustomerXcnd48")
    Integer findCPaymentCnt();

    @Query("select sum(numOfDelivery) from CustomerXcnd48")
    Integer findCDeliveryCnt();
}
