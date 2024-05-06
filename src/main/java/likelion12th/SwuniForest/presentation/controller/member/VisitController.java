package likelion12th.SwuniForest.presentation.controller.member;

import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.member.VisitService;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public void visited() {
        String major = memberService.getMyUserMajor();
        visitService.update_visitRate(major);
        log.info(major + ": 방문자수 +1");
    }
}
