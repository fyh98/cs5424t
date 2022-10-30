package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.Xcnd49.DistrictXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("districtRepositoryXcnd49")
public interface DistrictRepositoryXcnd49 extends JpaRepository<DistrictXcnd49, DistrictPK> {
    @Query("select sum(ytd) from DistrictXcnd49")
    BigDecimal findSumDYtd();

    @Query("select sum(nextAvailOrderNum) from DistrictXcnd49")
    Integer findSumDNextOId();
}
