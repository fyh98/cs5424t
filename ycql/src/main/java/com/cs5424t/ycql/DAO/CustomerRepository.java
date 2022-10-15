package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.Customer;
import com.cs5424t.ycql.Entities.PrimaryKeys.CustomerPK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CassandraRepository<Customer, CustomerPK> {
}
