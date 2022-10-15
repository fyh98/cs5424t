package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.OrderLine;
import com.cs5424t.ycql.Entities.PrimaryKeys.OrderLinePK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends CassandraRepository<OrderLine, OrderLinePK> {
}
