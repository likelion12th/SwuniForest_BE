package likelion12th.SwuniForest.service.visit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RankingDto {
    private String major;
    private Long visitCount;
    private Integer rank;
}
