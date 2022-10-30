package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.Xcnd49.DistrictXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("districtRepositoryXcnd49")
public interface DistrictRepositoryXcnd49 extends JpaRepository<DistrictXcnd49, DistrictPK> {
}
