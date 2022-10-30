package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.Xcnd45.CustomerXcnd45;
import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("customerRepositoryXcnd45")
public interface CustomerRepositoryXcnd45 extends JpaRepository<CustomerXcnd45, CustomerPK> {
    @Query("select sum(balance) from CustomerXcnd45")
    BigDecimal findSumCBalance();

    @Query("select sum(ytdPayment) from CustomerXcnd45")
    Float findSumCYtdPayment();

    @Query("select sum(numOfPayment) from CustomerXcnd45")
    Integer findCPaymentCnt();

    @Query("select sum(numOfDelivery) from CustomerXcnd45")
    Integer findCDeliveryCnt();
}
