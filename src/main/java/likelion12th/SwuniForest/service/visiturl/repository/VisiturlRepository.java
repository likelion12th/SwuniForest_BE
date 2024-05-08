package likelion12th.SwuniForest.service.visiturl.repository;

import likelion12th.SwuniForest.service.visiturl.domain.Visiturl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisiturlRepository extends JpaRepository<Visiturl, Long> {
}
