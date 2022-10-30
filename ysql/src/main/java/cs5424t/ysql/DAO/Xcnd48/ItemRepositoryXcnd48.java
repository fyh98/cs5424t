package cs5424t.ysql.DAO.Xcnd48;

import cs5424t.ysql.Entities.Xcnd48.ItemXcnd48;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("itemRepositoryXcnd48")
public interface ItemRepositoryXcnd48 extends JpaRepository<ItemXcnd48, Integer> {
}
