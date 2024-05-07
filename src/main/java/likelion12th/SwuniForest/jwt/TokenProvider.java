package likelion12th.SwuniForest.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import likelion12th.SwuniForest.service.member.domain.dto.CustomUserInfoDto;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${jwt.token.key}") private String secretKey;
    // 토큰 만료 시간
    private final long accessTokenValidTime = (60 * 1000) * 30; // 30분
    private Key key;

    // secretKey 인코딩
    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당
    // Base64(64진법) encode = 8비트 이진 데이터를 문자 코드에 영향을 받지 않는 공동 ASCII 영역의 문자들로만 이루어진 문자열로 변경
    // Base64(64진법) decode = ASCII 문자열을 8비트 이진 데이터로 변경
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Access Token 생성
    public String createAccessToken(CustomUserInfoDto customUserInfoDto) {
        return createToken(customUserInfoDto, accessTokenValidTime);
    }

    // JWT 토큰 생성
    public String createToken(CustomUserInfoDto customUserInfoDto, long accessTokenValidTime) {
        Claims claims = Jwts.claims();
        claims.put("studentNum", customUserInfoDto.getStudentNum());
        claims.put("major", customUserInfoDto.getMajor());
        claims.put("role", customUserInfoDto.getRole());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(accessTokenValidTime);

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant())) // set Expire Time 해당 옵션 안넣으면 expire 안함
                .signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
                .compact();
    }

    // 토큰에서 studentNum 추출
    public String getStudentNum(String token) {
        return parseClaims(token).get("studentNum", String.class);
    }

    // JWT Claims 추출 메소드
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {

            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {

            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {

            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}