package likelion12th.SwuniForest.util;

import likelion12th.SwuniForest.service.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


@Slf4j
public class SecurityUtil {

    // 생성자
    private SecurityUtil() {}

    // Request가 들어올 때 JwtFilter의 doFilter 메소드가
    // SecurityContext에 Authentication 객체를 저장해서 사용
    // 현재 요청을 보낸 회원의 저장된 Authentication 객체를 통해 username을 조회할 수 있음
    public static String getCurrentStudentNum() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }

        return authentication.getName();
    }

}
