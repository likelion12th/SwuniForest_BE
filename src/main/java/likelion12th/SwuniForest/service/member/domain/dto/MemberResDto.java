package likelion12th.SwuniForest.service.member.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResDto { // ocr api 호출 후 데이터 응답
    private String studentNum;
    private String username;
    private String major;
}
