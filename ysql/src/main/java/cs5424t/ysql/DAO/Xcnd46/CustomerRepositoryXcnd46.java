package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.Xcnd46.CustomerXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("customerRepositoryXcnd46")
public interface CustomerRepositoryXcnd46 extends JpaRepository<CustomerXcnd46, CustomerPK> {
    @Query("select sum(balance) from CustomerXcnd46")
    BigDecimal findSumCBalance();

    @Query("select sum(ytdPayment) from CustomerXcnd46")
    Float findSumCYtdPayment();

    @Query("select sum(numOfPayment) from CustomerXcnd46")
    Integer findCPaymentCnt();

    @Query("select sum(numOfDelivery) from CustomerXcnd46")
    Integer findCDeliveryCnt();
}
