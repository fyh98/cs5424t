package cs5424t.ysql.DAO.Xcnd46;

import cs5424t.ysql.Entities.Xcnd46.ItemXcnd46;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("itemRepositoryXcnd46")
public interface ItemRepositoryXcnd46 extends JpaRepository<ItemXcnd46, Integer> {
}
