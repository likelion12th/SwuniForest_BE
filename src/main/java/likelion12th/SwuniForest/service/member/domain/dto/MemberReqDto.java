package likelion12th.SwuniForest.service.member.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDto { // 회원가입 시 요청

    @NotNull
    @Size(min = 3, max = 50)
    private String studentNum;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    private String major;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 50)
    private String password;
}
