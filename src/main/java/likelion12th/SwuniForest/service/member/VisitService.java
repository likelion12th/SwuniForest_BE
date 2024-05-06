package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.Visit;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import likelion12th.SwuniForest.service.member.repository.VisitRepository;
import likelion12th.SwuniForest.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;

    // 방문율 업데이트
    // api 계속 호출하면 방문자 수 올라가는데 서비스 내에서 방문하기 버튼 한 번만 누를 수 있기 때문에
    // 굳이 boolean 변수 추가해야할까..? 추가하면 Member와 연관관계 생겨서 근데 하는게 나을 것 같긴함
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
