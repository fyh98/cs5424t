package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.Xcnd47.CustomerXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepositoryXcnd47")
public interface CustomerRepositoryXcnd47 extends JpaRepository<CustomerXcnd47, CustomerPK> {
}
