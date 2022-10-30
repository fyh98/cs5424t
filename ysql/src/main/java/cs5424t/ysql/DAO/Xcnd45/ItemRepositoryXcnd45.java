package cs5424t.ysql.DAO.Xcnd45;

import cs5424t.ysql.Entities.Xcnd45.ItemXcnd45;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("itemRepositoryXcnd45")
public interface ItemRepositoryXcnd45 extends JpaRepository<ItemXcnd45, Integer> {
}
