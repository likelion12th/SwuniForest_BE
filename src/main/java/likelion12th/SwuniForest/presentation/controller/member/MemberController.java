package likelion12th.SwuniForest.presentation.controller.member;

import jakarta.validation.Valid;
import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.member.domain.dto.MemberReqDto;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity signup(@Valid @RequestBody MemberReqDto memberReqDto) {
        try {
            MemberResDto memberResDto = memberService.signup(memberReqDto);
            return new ResponseEntity(memberResDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 현재 로그인한 회원 정보 조회
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<MemberResDto> getMyUserInfo() {
        try {
            MemberResDto memberResDto = memberService.getMyMemberInfo();
            return new ResponseEntity(memberResDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("현재 로그인한 사용자의 회원정보를 가져올 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 특정 회원 정보 조회 (관리자)
    // 필요할까..?
    @GetMapping("/user/{studentNum}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<MemberResDto> getUserInfo(@PathVariable String studentNum) {
        try {
            MemberResDto memberResDto = memberService.getMemberInfo(studentNum);
            return new ResponseEntity(memberResDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("조회한 회원의 정보를 가져올 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
