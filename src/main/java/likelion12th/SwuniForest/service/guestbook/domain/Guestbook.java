package likelion12th.SwuniForest.service.guestbook.domain;

import jakarta.persistence.*;
import likelion12th.SwuniForest.service.visit.domain.dto.VisitResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="guestbook_id")
    private Long id;

    // 방명록 작성자 이름
    private String guestName;

    // 방명록 내용
    @Column(nullable = false, length=50)
    private String guestContent;

    // 방명록 이미지
    private String fileName;

    // 익명 여부
    @Column(nullable = false)
    private boolean anonymous;


}
