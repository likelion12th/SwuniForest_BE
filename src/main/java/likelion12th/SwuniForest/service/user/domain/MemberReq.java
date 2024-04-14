package likelion12th.SwuniForest.service.user.domain;

import lombok.Getter;

@Getter
public class MemberReq { // 회원가입 시 요청
    private String studentNum;
    private String name;
    private String major;
    private String password;
}
