package cs5424t.ysql.DAO.Xcnd47;

import cs5424t.ysql.Entities.Xcnd47.ItemXcnd47;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("itemRepositoryXcnd47")
public interface ItemRepositoryXcnd47 extends JpaRepository<ItemXcnd47, Integer> {
}
