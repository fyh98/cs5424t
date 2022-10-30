package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.Xcnd48.DistrictXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("districtRepositoryXcnd48")
public interface DistrictRepositoryXcnd48 extends JpaRepository<DistrictXcnd48, DistrictPK> {
    @Query("select sum(ytd) from DistrictXcnd48")
    BigDecimal findSumDYtd();

    @Query("select sum(nextAvailOrderNum) from DistrictXcnd48")
    Integer findSumDNextOId();
}
