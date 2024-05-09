package likelion12th.SwuniForest.presentation.controller.member;


import jakarta.validation.Valid;
import likelion12th.SwuniForest.jwt.JwtFilter;
import likelion12th.SwuniForest.jwt.TokenProvider;
import likelion12th.SwuniForest.service.member.AuthService;
import likelion12th.SwuniForest.service.member.domain.dto.LoginDto;
import likelion12th.SwuniForest.service.member.domain.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    // 로그인 요청 시 jwt 토큰 발급
    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        try {
            // tokenDto를 이용해 response body에도 넣어서 리턴
            TokenDto tokenDto = authService.login(loginDto);
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
