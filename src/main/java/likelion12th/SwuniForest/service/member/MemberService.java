package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.Role;
import likelion12th.SwuniForest.service.member.domain.dto.MemberReqDto;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import likelion12th.SwuniForest.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public MemberResDto signup(MemberReqDto memberReqDto) {
        // unique 속성인 학번으로 이미 존재하는 회원인지 확인
        if (memberRepository.findByStudentNum(memberReqDto.getStudentNum()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 회원입니다.");
        }

        // 가입되어 있지 않은 회원이면,
        // 유저 정보를 만들어서 save
        // 권한은 USER
        Member member = Member.builder()
                .studentNum(memberReqDto.getStudentNum())
                .username(memberReqDto.getUsername())
                .major(memberReqDto.getMajor())
                .password(passwordEncoder.encode(memberReqDto.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        log.info("유저 생성");

        memberRepository.save(member);

        log.info("유저 저장");

        // entity -> dto 변환
        MemberResDto memberResDto = MemberResDto.builder()
                .studentNum(member.getStudentNum())
                .username(member.getUsername())
                .major(member.getMajor())
                .build();

        return memberResDto;
    }


    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    // 현재 로그인한 회원의 정보 가져오기
    @Transactional(readOnly = true)
    public Optional<Member> getMyMemberInfo() {
        return SecurityUtil.getCurrentStudentNum()
                .flatMap(memberRepository::findByStudentNum);
    }

    // 특정 회원 정보 조회 (관리자)
    @Transactional(readOnly = true)
    public MemberResDto getMemberInfo(String studentNum) {
        Optional<Member> memberOptional = memberRepository.findByStudentNum(studentNum);

        // 조회한 회원이 존재한다면
        if (memberOptional.isPresent()) {
            // member 객체 가져오기
            Member member = memberOptional.get();

            // entity -> dto 변환
            MemberResDto memberResDto = MemberResDto.builder()
                    .studentNum(member.getStudentNum())
                    .username(member.getUsername())
                    .major(member.getMajor())
                    .build();

            return memberResDto;

        } else { // 회원이 존재하지 않는 경우
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

    }


}
