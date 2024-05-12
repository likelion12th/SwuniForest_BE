package likelion12th.SwuniForest.service.visit;

import likelion12th.SwuniForest.service.lostitem.domain.dto.LostitemDto;
import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import likelion12th.SwuniForest.service.visit.domain.Visit;
import likelion12th.SwuniForest.service.visit.domain.dto.RankingDto;
import likelion12th.SwuniForest.service.visit.domain.dto.VisitResDto;
import likelion12th.SwuniForest.service.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final MemberRepository memberRepository;
    private final VisitRepository visitRepository;

    // 방문율 전체 랭킹 조회
    public List<RankingDto> getVisitRanking() {
        List<Visit> visitRankingList = visitRepository.findAllByOrderByVisitRateDesc();


        List<RankingDto> rankingDtoList = visitRankingList.stream()
                .map(visit -> RankingDto.builder()
                        .major(visit.getMajor())
                        .visitRate(visit.getVisitRate())
                        .rank(visit.getRanking())
                        .build())
                .collect(Collectors.toList());

        return rankingDtoList;
    }

    // 방문율 데이터 가져오기
    // 방문 상태 변경 + 방문율 데이터 가져오기
    public VisitResDto getVisitinfo(MemberResDto memberResDto) {
        Optional visitOptional = visitRepository.findByMajor(memberResDto.getMajor());
        Optional<Member> memberOptional = memberRepository.findByStudentNum(memberResDto.getStudentNum());
        Visit visit = (Visit) visitOptional.get();
        Member member = memberOptional.get();

        // 순위 업데이트 후 저장
        calculateRankingByVisitRate();

        return VisitResDto.builder()
                .username(member.getUsername())
                .major(member.getMajor())
                .visitRate(visit.getVisitRate())
                .rank(visit.getRanking())
                .build();
    }

    public VisitResDto update_visit(MemberResDto memberResDto) {
        Optional visitOptional = visitRepository.findByMajor(memberResDto.getMajor());
        Optional<Member> memberOptional = memberRepository.findByStudentNum(memberResDto.getStudentNum());

        // 요청한 사용자의 학과가 존재한다면
        if (visitOptional.isPresent()) {
            // 요청한 사용자의 학과 방문 데이터 업데이트
            Visit visit = (Visit) visitOptional.get();
            visit.addVisitor();
            visit.setVisitRate(visit.getVisitor(), visit.getTotalStudent());
            visitRepository.save(visit);

            // 방문하기 버튼을 누르기 위해선 로그인이 필수이므로 회원이 존재하지 않는 경우의 수는 고려하지 않음
            // 요청한 사용자 방문 여부 true 로 변경
            Member member = memberOptional.get();

            // 방문 여부 true로 변경
            member.visited();
            memberRepository.save(member);

            // 방문율 데이터 가져오기
            return getVisitinfo(memberResDto);


        } else { // 로그인한 회원의 학과가 존재하지 않는 경우
            throw new RuntimeException("존재하지 않는 학과입니다.");
        }
    }

    // 방문율을 기준으로 순위 산출
    private void calculateRankingByVisitRate() {
        List<Visit> allVisits = visitRepository.findAll();

        // 방문율을 기준으로 내림차순 정렬
        List<Visit> sortedVisits = allVisits.stream()
                .sorted((v1, v2) -> {
                    int compare = Long.compare(v2.getVisitRate(), v1.getVisitRate());
                    if (compare == 0) {
                        // 방문율이 동일한 경우에는 PK 내림차순으로 순위를 매김
                        return Long.compare(v2.getId(), v1.getId());
                    }
                    return compare;
                })
                .collect(Collectors.toList());

        // 순위 부여
        int ranking = 1;
        for (int i = 0; i < sortedVisits.size(); i++) {
            Visit visit = sortedVisits.get(i);
            if (i > 0 && visit.getVisitRate().equals(sortedVisits.get(i - 1).getVisitRate())) {
                // 이전 방문 데이터와 방문율이 동일한 경우 이전 순위를 그대로 유지
                visit.setRanking(sortedVisits.get(i - 1).getRanking());
            } else {
                visit.setRanking(ranking);
            }
            visitRepository.save(visit);
            ranking++;
        }
    }
}
