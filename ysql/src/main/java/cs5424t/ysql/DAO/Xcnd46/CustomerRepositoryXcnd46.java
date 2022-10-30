package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.Xcnd46.CustomerXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepositoryXcnd46")
public interface CustomerRepositoryXcnd46 extends JpaRepository<CustomerXcnd46, CustomerPK> {
}
