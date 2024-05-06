package likelion12th.SwuniForest.service.guestbook.repository;

import likelion12th.SwuniForest.service.guestbook.domain.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
}
