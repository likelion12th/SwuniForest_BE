package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.Role;
import likelion12th.SwuniForest.service.member.domain.dto.MemberReqDto;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import likelion12th.SwuniForest.service.stamp.domain.Stamp;
import likelion12th.SwuniForest.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResDto signup(MemberReqDto memberReqDto) {
        Optional optionalMember = memberRepository.findByStudentNum(memberReqDto.getStudentNum());

        // 이미 가입된 회원인지 확인
        if (optionalMember.isPresent()) {
            throw new RuntimeException("이미 존재하는 회원입니다.");
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
                .stamp(new Stamp()) // 서윤 추가
                .build();

        member.getStamp().setMember(member); // 서윤 추가
        log.info("유저 생성");

        memberRepository.save(member);

        log.info("유저 저장");

        // entity -> dto 변환
        MemberResDto memberResDto = MemberResDto.builder()
                .studentNum(member.getStudentNum())
                .username(member.getUsername())
                .major(member.getMajor())
                .visited(member.getVisited())
                .build();

        return memberResDto;
    }


    // 현재 로그인한 회원의 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResDto getMyMemberInfo() {
        String studentNum = SecurityUtil.getCurrentStudentNum();

        Optional<Member> currentMember = memberRepository.findByStudentNum(studentNum);

        // 조회한 회원이 존재한다면
        if (currentMember.isPresent()) {
            // member 객체 가져오기
            Member member = currentMember.get();

            // entity -> dto 변환
            MemberResDto memberResDto = MemberResDto.builder()
                    .studentNum(member.getStudentNum())
                    .username(member.getUsername())
                    .major(member.getMajor())
                    .visited(member.getVisited())
                    .build();

            return memberResDto;

        } else { // 회원이 존재하지 않는 경우
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }
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
                    .visited(member.getVisited())
                    .build();

            return memberResDto;

        } else { // 회원이 존재하지 않는 경우
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

    }

    public void update_visited(String studentNum) {
        Optional<Member> memberOptional = memberRepository.findByStudentNum(studentNum);

        // 조회한 회원이 존재한다면
        if (memberOptional.isPresent()) {
            // member 객체 가져오기
            Member member = memberOptional.get();

            // 방문 여부 true로 변경
            member.visited();
            memberRepository.save(member);

        } else { // 회원이 존재하지 않는 경우
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }
    }
}
