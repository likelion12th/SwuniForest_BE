package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.jwt.TokenProvider;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.dto.CustomUserInfoDto;
import likelion12th.SwuniForest.service.member.domain.dto.LoginDto;
import likelion12th.SwuniForest.service.member.domain.dto.TokenDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final TokenProvider tokenProvider; // = jwtUtil
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        String studentNum = loginDto.getStudentNum();
        String password = loginDto.getPassword();
        Optional<Member> optionalMember = memberRepository.findByStudentNum(studentNum);

        // optionalMember가 존재하지 않는 경우 예외 발생
        Member member = optionalMember.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));

        // 비밀번호 비교 후 일치 시 토큰 반환
        if (!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        TokenDto tokenDto = TokenDto.builder()
                .token(createAccessToken(member))
                .role(member.getRole().toString())
                .build();

        return tokenDto;
    }

    private String createAccessToken(Member member) {
        CustomUserInfoDto customUserInfoDto = CustomUserInfoDto.builder()
                .studentNum(member.getStudentNum())
                .major(member.getMajor())
                .role(member.getRole())
                .build();

        return tokenProvider.createAccessToken(customUserInfoDto);
    }

}
