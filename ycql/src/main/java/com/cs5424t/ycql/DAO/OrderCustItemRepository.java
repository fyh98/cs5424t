package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.OrderCustItem;
import com.cs5424t.ycql.Entities.PrimaryKeys.OrderCustItemPK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCustItemRepository extends CassandraRepository<OrderCustItem, OrderCustItemPK>{
}
