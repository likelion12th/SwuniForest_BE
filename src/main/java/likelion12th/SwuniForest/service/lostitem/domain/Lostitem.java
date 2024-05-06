package likelion12th.SwuniForest.service.lostitem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lostitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lostitem_id")
    private Long id;

    // 분실물 게시판 글 제목
    @Column(nullable = false, length=30)
    private String itemTitle;

    // 분실물 게시판 글 내용
    @Column(nullable = false, length=50)
    private String itemContent;

    // 분실물 발견한 곳
    private String findPoint;

    // 분실물 맡긴 곳
    private String putPoint;

    // 분실물 게시판 이미지
    private String fileName;

}
