package likelion12th.SwuniForest.service.guestbook.domain.dto;

import likelion12th.SwuniForest.service.visit.domain.dto.VisitResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestbookDto { // 방명록 작성 시 요청
    private String guestName;
    private String guestContent;
    private String fileName;
    private boolean anonymous;

    public String getGuestName() {
        return guestName;
    }

    public String getGuestContent() {
        return guestContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

}
