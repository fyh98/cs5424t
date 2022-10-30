package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.Xcnd45.CustomerXcnd45;
import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepositoryXcnd45")
public interface CustomerRepositoryXcnd45 extends JpaRepository<CustomerXcnd45, CustomerPK> {
}
