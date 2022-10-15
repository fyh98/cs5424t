package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.Item;
import com.cs5424t.ycql.Entities.PrimaryKeys.ItemPK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CassandraRepository<Item, ItemPK> {
}
