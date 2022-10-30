package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.Xcnd49.CustomerXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("customerRepositoryXcnd49")
public interface CustomerRepositoryXcnd49 extends JpaRepository<CustomerXcnd49, CustomerPK> {
    @Query("select sum(balance) from CustomerXcnd49")
    BigDecimal findSumCBalance();

    @Query("select sum(ytdPayment) from CustomerXcnd49")
    Float findSumCYtdPayment();

    @Query("select sum(numOfPayment) from CustomerXcnd49")
    Integer findCPaymentCnt();

    @Query("select sum(numOfDelivery) from CustomerXcnd49")
    Integer findCDeliveryCnt();
}
