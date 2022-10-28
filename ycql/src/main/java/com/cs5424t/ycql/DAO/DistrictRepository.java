package com.cs5424t.ycql.DAO;

import com.cs5424t.ycql.Entities.District;
import com.cs5424t.ycql.Entities.PrimaryKeys.DistrictPK;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DistrictRepository extends CassandraRepository<District, DistrictPK> {
	@Query("update district_ycql set d_next_o_id = ?0 where d_w_id = ?1 and d_id = ?2")
    void updateNextAvalNumById(Integer N, Integer warehouseId, Integer districtId);

    @Query("select sum(d_ytd) from district_ycql;")
    BigDecimal findSumDYtd();

    @Query("select sum(d_next_o_id) from district_ycql;")
    Integer findSumDNextOId();
}
