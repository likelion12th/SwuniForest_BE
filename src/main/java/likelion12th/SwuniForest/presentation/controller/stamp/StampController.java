package likelion12th.SwuniForest.presentation.controller.stamp;

import likelion12th.SwuniForest.service.stamp.StampService;
import likelion12th.SwuniForest.service.stamp.domain.dto.StampDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stamp")
public class StampController {
    private final StampService stampService;
    // 학과 코드로 도장 찍기
    @PostMapping("/check")
    public ResponseEntity<String> checkStamp( @RequestParam("depCode") String depCode) {
        try {
            stampService.checkStamp(depCode);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스템프 처리 실패");
        }

        return ResponseEntity.ok("스템프 처리 성공 " + depCode );
    }

    // 로그인한 유저의 스템프판 조회 - 모든 학과에 대한 조회
    @GetMapping("/all")
    public ResponseEntity<StampDto> stampBoard(){
        return ResponseEntity.ok(stampService.stampBoard());
    }
}
