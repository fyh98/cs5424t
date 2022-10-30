package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.Xcnd45.DistrictXcnd45;
import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("districtRepositoryXcnd45")
public interface DistrictRepositoryXcnd45 extends JpaRepository<DistrictXcnd45, DistrictPK> {
    @Query("select sum(ytd) from DistrictXcnd45")
    BigDecimal findSumDYtd();

    @Query("select sum(nextAvailOrderNum) from DistrictXcnd45")
    Integer findSumDNextOId();
}
