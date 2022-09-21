package cs5424t.ysql.DAO;

import cs5424t.ysql.Entities.Customer;
import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, CustomerPK> {
}
