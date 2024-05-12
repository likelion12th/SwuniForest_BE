package likelion12th.SwuniForest.presentation.controller.lostitem;

import likelion12th.SwuniForest.service.S3Service;
import likelion12th.SwuniForest.service.guestbook.domain.dto.GuestbookDto;
import likelion12th.SwuniForest.service.lostitem.LostitemService;
import likelion12th.SwuniForest.service.lostitem.domain.dto.LostitemDto;
import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lostitem")
public class LostitemController {

    private final LostitemService lostitemService;
    private final MemberService memberService;
    private final S3Service s3Service;

    // 분실물 게시판 글 작성
    @PostMapping("/post")
    public ResponseEntity<String> postLostitem(
            @RequestPart(value = "lostitemDto") LostitemDto lostitemDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        String fileName = "";
        if (imageFile != null && !imageFile.isEmpty()) {
            // 이미지 업로드
            try {
                // S3 버킷의 guestbook 디렉토리 안에 저장됨
                fileName = s3Service.upload(imageFile, "lostitem");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 중 오류가 발생했습니다.");
            }
        }

        // 글 등록
        try {
            LostitemDto response = lostitemService.createLostitem(lostitemDto, fileName);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("분실물 게시판에 글 작성 중 오류가 발생했습니다.");
        }
    }

    // 분실물 게시판 글 전체 조회
    @GetMapping("/")
    public ResponseEntity getAllLostitem(){
        try {
            List<LostitemDto> lostitemList = lostitemService.getAllLostitem();
            return ResponseEntity.ok(lostitemList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
