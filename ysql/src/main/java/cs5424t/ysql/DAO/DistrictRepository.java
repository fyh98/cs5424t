package cs5424t.ysql.DAO;

import cs5424t.ysql.Entities.District;
import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, DistrictPK> {
}
