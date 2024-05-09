package likelion12th.SwuniForest.presentation.controller.member;

import likelion12th.SwuniForest.service.member.ClovaOcrApiService;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class OcrController {

    private final ClovaOcrApiService clovaOcrApiService;


    @PostMapping("/naverOcrPost")
    public ResponseEntity ocr(
            @RequestPart(value="imageFile", required = false) MultipartFile imageFile) throws IOException {

        MemberResDto memberResDto = clovaOcrApiService.doOcr(imageFile);

        // OCR 결과 처리
        if (memberResDto != null) {
            return new ResponseEntity(memberResDto, HttpStatus.OK);
        } else {
            log.info("OCR failed");
            return new ResponseEntity("OCR failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

