package likelion12th.SwuniForest.service.stamp.repository;


import likelion12th.SwuniForest.service.stamp.domain.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    Optional<Certification> findByCode(String code);

}