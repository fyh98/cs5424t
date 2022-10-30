package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.Xcnd49.CustomerXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepositoryXcnd49")
public interface CustomerRepositoryXcnd49 extends JpaRepository<CustomerXcnd49, CustomerPK> {
}
