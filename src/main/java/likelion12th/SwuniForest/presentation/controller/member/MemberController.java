package likelion12th.SwuniForest.presentation.controller.member;

import jakarta.validation.Valid;
import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.dto.LoginDto;
import likelion12th.SwuniForest.service.member.domain.dto.MemberReqDto;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResDto> signup(@Valid @RequestBody MemberReqDto memberReqDto) {
        return ResponseEntity.ok(memberService.signup(memberReqDto));
    }

    // 회원 정보 조회 (마이페이지)
    // 현재 구현 페이지 상으로는 필요없는 기능 같다.
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Member> getMyUserInfo() {
        return ResponseEntity.ok(memberService.getMyMemberInfo().get());
    }

    // 특정 회원 정보 조회 (관리자)
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberResDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(memberService.getMemberInfo(username));
    }
}
