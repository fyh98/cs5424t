package cs5424t.ysql.DAO;

import cs5424t.ysql.Entities.OrderLine;
import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLinePK> {

}
