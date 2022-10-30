package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.Xcnd45.DistrictXcnd45;
import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.Xcnd46.DistrictXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("districtRepositoryXcnd46")
public interface DistrictRepositoryXcnd46 extends JpaRepository<DistrictXcnd46, DistrictPK> {
}
