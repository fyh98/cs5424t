package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.Customer;
import com.cs5424t.ycql.Entities.PrimaryKeys.CustomerPK;

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
	 
}
