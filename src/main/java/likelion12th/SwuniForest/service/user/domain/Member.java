package likelion12th.SwuniForest.service.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    // 학번
    private String studentNum;

    // 이름
    private String name;

    // 학과
    private String major;

    // 비밀번호
    private String password;
}
