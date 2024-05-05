package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.jwt.TokenProvider;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.dto.CustomUserInfoDto;
import likelion12th.SwuniForest.service.member.domain.dto.LoginDto;
import likelion12th.SwuniForest.service.member.domain.dto.MemberReqDto;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String login(LoginDto loginDto) {
        String studentNum = loginDto.getStudentNum();
        String password = loginDto.getPassword();
        Optional<Member> optionalMember = memberRepository.findByStudentNum(studentNum);
        Member member = optionalMember.get();

        if (member == null) {
            throw new UsernameNotFoundException("해당 회원이 존재하지 않습니다.");
        }

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if (!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        CustomUserInfoDto customUserInfoDto = CustomUserInfoDto.builder()
                .studentNum(member.getStudentNum())
                .major(member.getMajor())
                .role(member.getRole())
                .build();

        String accessToken = tokenProvider.createAccessToken(customUserInfoDto);
        return accessToken;
    }


}
