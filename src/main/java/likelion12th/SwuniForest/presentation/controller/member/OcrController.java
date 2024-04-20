package likelion12th.SwuniForest.presentation.controller.member;

import likelion12th.SwuniForest.service.member.ClovaOcrApi;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OcrController { // 배포 후에 로컬 경로가 아닌 s3 버킷으로 변경 필요

//    // AWS S3 관련 설정
//    @Value("${aws.s3.bucketName}")
//    private String bucketName;
//    private final AmazonS3 amazonS3;

    private final ClovaOcrApi ocrApi;
    @Value("${naver.service.secretKey}")
    private String secretKey;

    @Value("${uploadPath}")
    private String uploadPath;

    @PostMapping("/naverOcrPost")
    public ResponseEntity ocr(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity("No file uploaded", HttpStatus.BAD_REQUEST);
        }

        String fileName = file.getOriginalFilename();
        String filePath = uploadPath + "/" + fileName;

        // MultipartFile -> File 변환 후 저장
        file.transferTo(new File(filePath));


        // 방금 저장한 파일 불러오기
        File savedFile = ResourceUtils.getFile(filePath);
        // 요청받은 파일의 확장자를 file.getContentType() 으로 조회하면 PNG같은 확장자가 아닌 multipart/form-data 로 들어가서 OCR API 호출 시 400 에러가 발생한다.
        // 저장한 File 객체의 확장자를 불러오기 위해 지정된 메소드가 없기 떄문에 살짝은.. 끼워맞추기 식으로 문자열 추출을 하였다.
        String ext = "";

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            ext = fileName.substring(lastDotIndex + 1);
        }

        // OCR API 호출
        MemberResDto memberRes = ocrApi.callApi("POST", savedFile.getPath(), secretKey, ext);

        // OCR 결과 처리
        if (memberRes != null) {
            return new ResponseEntity(memberRes, HttpStatus.OK);
        } else {
            log.info("OCR failed");
            return new ResponseEntity("OCR failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

