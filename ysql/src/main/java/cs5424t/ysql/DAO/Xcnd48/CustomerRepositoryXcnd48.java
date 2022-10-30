package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.Xcnd48.CustomerXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepositoryXcnd48")
public interface CustomerRepositoryXcnd48 extends JpaRepository<CustomerXcnd48, CustomerPK> {
}
