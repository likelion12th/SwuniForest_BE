package likelion12th.SwuniForest.service.stamp.repository;

import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.stamp.domain.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {
    Optional<Stamp> findByMember(Member member);
}