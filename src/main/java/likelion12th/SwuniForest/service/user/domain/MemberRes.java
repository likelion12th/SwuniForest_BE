package likelion12th.SwuniForest.service.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRes { // ocr api 호출 후 데이터 응답
    private String studentNum;
    private String name;
    private String major;
}
