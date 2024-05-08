package likelion12th.SwuniForest.service.member.domain;

import jakarta.persistence.*;
import likelion12th.SwuniForest.service.stamp.domain.Stamp;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    // 학번
    @Column(length = 50, unique = true)
    private String studentNum;

    // 이름
    @Column(length = 50)
    private String username;

    // 학과
    @Column(nullable = false)
    private String major;

    // 비밀번호
    @Column(length = 300)
    private String password;

    // 방문 여부
    @Builder.Default
    private Boolean visited = false;

    // 권한
    @Enumerated(EnumType.STRING)
    private Role role;

    // 일대일 연관관계 - 도장판
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stamp stamp;

    public Member(String subject, String s, Collection<? extends GrantedAuthority> authorities) {
    }

    // 방문 여부 변경 메소드
    public void visited() {
        this.visited = true;
    }
}
