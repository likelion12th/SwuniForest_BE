package likelion12th.SwuniForest.service.member;

import likelion12th.SwuniForest.service.member.domain.dto.LoginDto;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    @Transactional
    String login(LoginDto loginDto);
}
