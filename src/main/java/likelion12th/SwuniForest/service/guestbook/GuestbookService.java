package likelion12th.SwuniForest.service.guestbook;

import likelion12th.SwuniForest.service.guestbook.domain.Guestbook;
import likelion12th.SwuniForest.service.guestbook.domain.dto.GuestbookDto;
import likelion12th.SwuniForest.service.guestbook.repository.GuestbookRepository;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.Role;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;

    // 방명록 작성
    @Transactional
    public GuestbookDto createGuestbook(GuestbookDto guestbookDto, String fileName) {

        // 클라이언트로부터 전송된 요청에서 익명 여부를 확인
        boolean isAnonymous = guestbookDto.isAnonymous();

        // 사용자가 익명으로 글을 작성한 경우, 이름을 "익명"으로 설정
        String guestName = isAnonymous ? "익명" : guestbookDto.getGuestName();

        // 방명록 객체 생성
        Guestbook guestbook = Guestbook.builder()
                .guestName(guestName)
                .guestContent(guestbookDto.getGuestContent())
                .fileName(fileName)
                .anonymous(guestbookDto.isAnonymous())
                .build();

        System.out.println("name = " + guestbook.getGuestName());
        System.out.println("content = " + guestbook.getGuestContent());

        // 데이터베이스에 방명록 저장
        guestbookRepository.save(guestbook);

        // 저장된 방명록의 정보를 guestbookRes(dto)로 변환하여 반환
        GuestbookDto guestbookRes = GuestbookDto.builder()
                .guestName(guestbook.getGuestName())
                .guestContent(guestbook.getGuestContent())
                .fileName(guestbook.getFileName())
                .anonymous(guestbook.isAnonymous())
                .build();

        return guestbookRes;
    }

}
