package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.Customer;
import com.cs5424t.ycql.Entities.PrimaryKeys.CustomerPK;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CassandraRepository<Customer, CustomerPK> {
	@Query("select * from customer_ycql;")
	 List<Customer> findAllCustomer();
	 @Query("select c_w_id, c_d_id, c_id from customer_ycql where c_w_id != ?0")
	 List<CustomerPK> findPossibleCustomers(Integer warehouseId);
	 @Query("select * from customer_test_ycql where c_w_id = ?0 order by c_balance,c_d_id,c_id limit 10")
	 List <Customer> findTopCustomer(Integer warehouseId);

	 @Query("select sum(c_balance) from customer_ycql;")
	 BigDecimal findSumCBalance();

	 @Query("select sum(c_ytd_payment) from customer_ycql;")
	 Float findSumCYtdPayment();

	 @Query("select sum(c_payment_cnt) from customer_ycql;")
	 Integer findCPaymentCnt();

	 @Query("select sum(c_delivery_cnt) from customer_ycql;")
	 Integer findCDeliveryCnt();
}
