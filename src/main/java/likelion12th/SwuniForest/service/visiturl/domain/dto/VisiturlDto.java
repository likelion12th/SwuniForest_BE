package likelion12th.SwuniForest.service.visiturl.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisiturlDto {
    private int totalVisitCount;
    private int todayVisitCount;
    private LocalDate visitDate;
}
