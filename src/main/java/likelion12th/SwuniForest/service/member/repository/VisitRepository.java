package likelion12th.SwuniForest.service.member.repository;

import likelion12th.SwuniForest.service.member.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByMajor(String major);
}
