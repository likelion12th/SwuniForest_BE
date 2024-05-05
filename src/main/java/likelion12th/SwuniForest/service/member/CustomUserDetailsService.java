package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.service.member.domain.CustomUserDetails;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.Role;
import likelion12th.SwuniForest.service.member.domain.dto.CustomUserInfoDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    // 로그인 시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    // loadUserByUsername은 이미 존재하는 메소드를 오버라이딩 하는 것이기 때문에 메소드명 변경이 불가함
    // loadUserByUsername = loadMemberByStudentNum 이라고 생각하면 된다.
    // 6번
    public UserDetails loadUserByUsername(String studentNum) throws UsernameNotFoundException {
        Member member = memberRepository.findByStudentNum(studentNum)
                .orElseThrow(() -> new UsernameNotFoundException(studentNum + " -> 데이터베이스에서 찾을 수 없습니다."));

        CustomUserInfoDto customUserInfoDto = CustomUserInfoDto.builder()
                .studentNum(member.getStudentNum())
                .password(member.getPassword())
                .major(member.getMajor())
                .role(member.getRole())
                .build();

        return new CustomUserDetails(customUserInfoDto);
    }
}
