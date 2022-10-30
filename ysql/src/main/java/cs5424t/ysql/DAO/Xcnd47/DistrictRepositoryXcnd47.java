package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.Xcnd47.DistrictXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("districtRepositoryXcnd47")
public interface DistrictRepositoryXcnd47 extends JpaRepository<DistrictXcnd47, DistrictPK> {
}
