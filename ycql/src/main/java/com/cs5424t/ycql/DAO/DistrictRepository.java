package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.District;
import com.cs5424t.ycql.Entities.PrimaryKeys.DistrictPK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends CassandraRepository<District, DistrictPK> {
	
}
