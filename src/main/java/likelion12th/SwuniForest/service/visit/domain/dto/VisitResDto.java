package likelion12th.SwuniForest.service.visit.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisitResDto { // 방문하기 후 리턴할 방문율 dto
    private String username;
    private String major;
    // private Long visitRate;
    private Long visitCount;
    private Integer rank;
}
