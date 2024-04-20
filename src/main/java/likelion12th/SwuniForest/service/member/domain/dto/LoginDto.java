package likelion12th.SwuniForest.service.member.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 30)
    private String studentNum;

    @NotNull
    @Size(min = 3, max = 30)
    private String password;
}
