package likelion12th.SwuniForest.service.visit.repository;

import likelion12th.SwuniForest.service.visit.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByMajor(String major);

    List<Visit> findAllByOrderByVisitCountDesc();
}
