package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.Xcnd48.DistrictXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("districtRepositoryXcnd48")
public interface DistrictRepositoryXcnd48 extends JpaRepository<DistrictXcnd48, DistrictPK> {
}
