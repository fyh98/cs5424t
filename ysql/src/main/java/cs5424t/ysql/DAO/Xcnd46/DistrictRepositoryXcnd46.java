package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.Xcnd45.DistrictXcnd45;
import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.Xcnd46.DistrictXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("districtRepositoryXcnd46")
public interface DistrictRepositoryXcnd46 extends JpaRepository<DistrictXcnd46, DistrictPK> {
    @Query("select sum(ytd) from DistrictXcnd46")
    BigDecimal findSumDYtd();

    @Query("select sum(nextAvailOrderNum) from DistrictXcnd46")
    Integer findSumDNextOId();
}
