package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.service.member.domain.dto.LoginDto;
import likelion12th.SwuniForest.service.member.domain.dto.TokenDto;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    @Transactional
    TokenDto login(LoginDto loginDto);
}
