package likelion12th.SwuniForest.presentation.controller.visit;

import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.visit.VisitService;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.visit.domain.dto.RankingDto;
import likelion12th.SwuniForest.service.visit.domain.dto.VisitResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/visit")
public class VisitController {

    private final MemberService memberService;
    private final VisitService visitService;

    // 방문하기
    @PostMapping("/change")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity visited() {
        MemberResDto memberResDto = memberService.getMyMemberInfo();

        // 이미 방문하기 눌렀다면
        if (memberResDto.getVisited()) {
            return new ResponseEntity("이미 방문하기를 누르셨습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            VisitResDto visitResDto = visitService.update_visit(memberResDto);
            // 방문하기 성공하면 방문율 데이터 dto 리턴
            return new ResponseEntity(visitResDto, HttpStatus.OK);
        }
    }

    // 방문율 데이터 불러오기
    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity getVisitInfo() {
        MemberResDto memberResDto = memberService.getMyMemberInfo();
        VisitResDto visitResDto = visitService.getVisitinfo(memberResDto);
        return new ResponseEntity(visitResDto, HttpStatus.OK);
    }

    // 방문율 전체 랭킹 조회 (권한 필요 없음)
    @GetMapping("")
    public ResponseEntity getVisitRanking() {

        List<RankingDto> rankingDtoList = visitService.getVisitRanking();
        return ResponseEntity.ok(rankingDtoList);
    }
}
