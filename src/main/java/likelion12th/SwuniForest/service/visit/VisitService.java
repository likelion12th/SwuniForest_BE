package likelion12th.SwuniForest.service.visit;

import likelion12th.SwuniForest.service.visit.domain.Visit;
import likelion12th.SwuniForest.service.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;

    // 방문율 업데이트
    public void update_visitRate(String major) {
        Optional visitOptional = visitRepository.findByMajor(major);

        if (visitOptional.isPresent()) {
            Visit visit = (Visit) visitOptional.get();
            visit.addVisitor();
            visitRepository.save(visit);

        } else { // 로그인한 회원의 학과가 존재하지 않는 경우
            throw new RuntimeException("존재하지 않는 학과입니다.");
        }

    }
}
