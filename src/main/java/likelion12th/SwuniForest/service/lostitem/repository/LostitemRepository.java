package likelion12th.SwuniForest.service.lostitem.repository;

import likelion12th.SwuniForest.service.lostitem.domain.Lostitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostitemRepository extends JpaRepository<Lostitem, Long> {
}
