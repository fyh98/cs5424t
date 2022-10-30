package cs5424t.ysql.DAO.Xcnd49;

import cs5424t.ysql.Entities.Xcnd49.ItemXcnd49;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("itemRepositoryXcnd49")
public interface ItemRepositoryXcnd49 extends JpaRepository<ItemXcnd49, Integer> {
}
