package likelion12th.SwuniForest.service.stamp.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CertificationDto {
    private String department;
    private String code;
    private String expirationTime;
}
