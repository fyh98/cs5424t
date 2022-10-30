package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.Xcnd45.DistrictXcnd45;
import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("districtRepositoryXcnd45")
public interface DistrictRepositoryXcnd45 extends JpaRepository<DistrictXcnd45, DistrictPK> {
}
