package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.Order;
import com.cs5424t.ycql.Entities.PrimaryKeys.OrderPK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CassandraRepository<Order, OrderPK> {
}
