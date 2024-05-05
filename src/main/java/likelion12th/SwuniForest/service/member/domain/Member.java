package likelion12th.SwuniForest.service.member.domain;

import jakarta.persistence.*;
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
    @Column(length = 50, nullable = false, unique = true)
    private String studentNum;

    // 이름
    @Column(length = 50, nullable = false)
    private String username;

    // 학과
    @Column(nullable = false)
    private String major;

    // 비밀번호
    @Column(length = 300, nullable = false)
    private String password;

    // 권한
    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String subject, String s, Collection<? extends GrantedAuthority> authorities) {
    }
}
