package likelion12th.SwuniForest.presentation.controller.member;

import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.member.VisitService;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VisitController {

    private final MemberService memberService;
    private final VisitService visitService;

    // 방문하기
    @GetMapping("/visit")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<String> visited() {
        MemberResDto memberResDto = memberService.getMyMemberInfo();

        // 이미 방문하기 눌렀다면
        if (memberResDto.getVisited()) {
            return new ResponseEntity("이미 방문하기를 누르셨습니다.", HttpStatus.OK);
        } else {
            // 특정 학과 방문자 수 증가
            visitService.update_visitRate(memberResDto.getMajor());
            // 요청한 사용자 방문 여부 필드 false 로 변경
            memberService.update_visited(memberResDto.getStudentNum());
            return new ResponseEntity("방문하기 성공", HttpStatus.OK);
        }
    }
}
