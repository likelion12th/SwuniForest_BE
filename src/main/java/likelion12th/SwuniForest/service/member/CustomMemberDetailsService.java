package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.Role;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    // 로그인 시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    // loadUserByUsername은 이미 존재하는 메소드를 오버라이딩 하는 것이기 때문에 메소드명 변경이 불가함
    // loadUserByUsername = loadMemberByStudentNum 이라고 생각하면 된다.
    // 6번
    public UserDetails loadUserByUsername(final String studentNum) {

        return memberRepository.findByStudentNum(studentNum)
                .map(member -> createUser(studentNum, member))
                .orElseThrow(() -> new UsernameNotFoundException(studentNum + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String studentNum, Member member) {
        Role role = member.getRole(); // 회원의 역할을 가져옵니다.

        // 권한 문자열을 기반으로 GrantedAuthority 객체를 생성합니다.
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.name());

        // 권한까지 리스트로 감싸서 Spring Security의 UserDetails 객체를 생성합니다.
        // spring security는 username 이라는 항목을 unique 로 사용하는데 나는 studentNum이 unique 이므로
        // username 자리에 studentNum을 넣어주었다.
        // Spring Security에서 사용자의 권한은 리스트 형태로 관리된다.
        // 하지만 현재 프로젝트에서는 사용자가 하나의 역할만 가질 수 있으므로,
        // 단일 요소의 리스트(singletonList)를 생성하여 해당 역할을 UserDetails 객체에 할당
        return new org.springframework.security.core.userdetails.User(
                member.getStudentNum(), member.getPassword(), Collections.singletonList(grantedAuthority));
    }
}
