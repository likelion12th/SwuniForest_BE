package likelion12th.SwuniForest.presentation.controller.guestbook;

import likelion12th.SwuniForest.service.guestbook.GuestbookService;
import likelion12th.SwuniForest.service.guestbook.S3Service;
import likelion12th.SwuniForest.service.guestbook.domain.dto.GuestbookDto;
import likelion12th.SwuniForest.service.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guestbook")
public class GuestbookController {

    private final GuestbookRepository guestbookRepository;
    private final GuestbookService guestbookService;
    private final S3Service s3Service;

    // 방명록 작성
    @PostMapping("/post")
    public ResponseEntity<String> postGuestbook(
            @RequestPart(value = "guestbookDto") GuestbookDto guestbookDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        String fileName = "";
        if (imageFile != null && !imageFile.isEmpty()) {
            // 이미지 업로드
            try {
                // S3 버킷의 guestbook 디렉토리 안에 저장됨
                fileName = s3Service.upload(imageFile, "guestbook");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 중 오류가 발생했습니다.");
            }
        }

        // 방명록 등록
        try {
            GuestbookDto response = guestbookService.createGuestbook(guestbookDto, fileName);
            return ResponseEntity.ok("방명록이 성공적으로 작성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("방명록 작성 중 오류가 발생했습니다.");
        }
    }

    // 방명록 전체 조회
    @GetMapping("/")
    public ResponseEntity<List<GuestbookDto>> getAllGuestbook(){
        try {
            List<GuestbookDto> guestbookList = guestbookService.getAllGuestbook();
            return ResponseEntity.ok(guestbookList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
