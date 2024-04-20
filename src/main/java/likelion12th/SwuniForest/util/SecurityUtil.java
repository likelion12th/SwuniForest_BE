package likelion12th.SwuniForest.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


@Slf4j
public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    // 생성자
    private SecurityUtil() {}

    // getCurrentUsername 메소드의 역할은 Security Cont
    public static Optional<String> getCurrentStudentNum() {

        // Request가 들어올 때 JwtFilter의 doFilter 메소드가
        // SecurityContext에 Authentication 객체를 저장해서 사용
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String studentNum = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            studentNum = springSecurityUser.getUsername();
            log.info(studentNum);
        } else if (authentication.getPrincipal() instanceof String) {
            studentNum = (String) authentication.getPrincipal();
            log.info(studentNum);
        }



        return Optional.ofNullable(studentNum);
    }
}
