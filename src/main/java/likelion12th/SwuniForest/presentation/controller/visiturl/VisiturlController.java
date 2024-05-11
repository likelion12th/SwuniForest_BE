package likelion12th.SwuniForest.presentation.controller.visiturl;

import jakarta.servlet.http.HttpServletRequest;
import likelion12th.SwuniForest.service.visiturl.VisiturlService;
import likelion12th.SwuniForest.service.visiturl.domain.dto.VisiturlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VisiturlController {
    private final VisiturlService visiturlService;

    // 방문자 수 증가
    @GetMapping("/")
    public VisiturlDto visit(HttpServletRequest request) {
        visiturlService.incrementVisitCount(request);
        return visiturlService.getVisitCounts();
    }

}
