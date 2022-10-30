package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.Xcnd47.CustomerXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("customerRepositoryXcnd47")
public interface CustomerRepositoryXcnd47 extends JpaRepository<CustomerXcnd47, CustomerPK> {
    @Query("select sum(balance) from CustomerXcnd47")
    BigDecimal findSumCBalance();

    @Query("select sum(ytdPayment) from CustomerXcnd47")
    Float findSumCYtdPayment();

    @Query("select sum(numOfPayment) from CustomerXcnd47")
    Integer findCPaymentCnt();

    @Query("select sum(numOfDelivery) from CustomerXcnd47")
    Integer findCDeliveryCnt();
}
