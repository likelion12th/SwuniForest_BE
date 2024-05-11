package likelion12th.SwuniForest.service.lostitem.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LostitemDto {
    private String itemTitle;

    // 분실물 게시판 이미지
    private String fileName;

    // 분실물 발견한 곳(주운 곳)
    private String findPoint;

    // 분실물 맡긴 곳
    private String putPoint;

    // 게시글 작성 시간
    private LocalDateTime createdAt;
}
