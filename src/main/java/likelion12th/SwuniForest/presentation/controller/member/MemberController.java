package likelion12th.SwuniForest.presentation.controller.member;

import jakarta.validation.Valid;
import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.dto.MemberReqDto;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResDto> signup(@Valid @RequestBody MemberReqDto memberReqDto) {
        return ResponseEntity.ok(memberService.signup(memberReqDto));
    }

    // 현재 로그인한 회원 정보 조회
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<MemberResDto> getMyUserInfo() {
        return ResponseEntity.ok(memberService.getMyMemberInfo());
    }


    // 특정 회원 정보 조회 (관리자)
    @GetMapping("/user/{studentNum}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<MemberResDto> getUserInfo(@PathVariable String studentNum) {
        return ResponseEntity.ok(memberService.getMemberInfo(studentNum));
    }
}
