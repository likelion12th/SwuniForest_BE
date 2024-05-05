package likelion12th.SwuniForest.service.member.domain.dto;

import likelion12th.SwuniForest.service.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserInfoDto {
    private String studentNum;
    private String password;
    private String major;
    private Role role;
}
