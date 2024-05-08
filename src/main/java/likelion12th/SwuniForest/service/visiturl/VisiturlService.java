package likelion12th.SwuniForest.service.visiturl;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import likelion12th.SwuniForest.service.visiturl.domain.Visiturl;
import likelion12th.SwuniForest.service.visiturl.domain.dto.VisiturlDto;
import likelion12th.SwuniForest.service.visiturl.repository.VisiturlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class VisiturlService {
    private final VisiturlRepository visiturlRepository;

    // 방문자 수 조회
    public VisiturlDto getVisitCounts() {
        Visiturl visiturl = visiturlRepository.findById(1L)
                .orElse(new Visiturl());

        // Dto로 변환하여 누적 방문자수, 투데이 방문자수 반환
        return new VisiturlDto(visiturl.getTotalVisitCount()
                , visiturl.getTodayVisitCount()
                , visiturl.getVisitDate());
    }


    // 방문자 수 증가 처리
    public void incrementVisitCount(HttpServletRequest request) {
        // 세션 
        HttpSession session = request.getSession();

        if (session.getAttribute("visited") == null) {
            
            // 세션에 visited 속성을 부여하여 새로고침 시 방문자 수 증가 방지
            session.setAttribute("visited", true);

            Visiturl visiturl = visiturlRepository.findById(1L).orElse(new Visiturl());
            visiturl.setTotalVisitCount(visiturl.getTotalVisitCount() + 1);

            // 현재 날짜 정보 가져오기
            LocalDate currentDate = LocalDate.now();

            // 투데이 방문자 수 초기화
            if (!currentDate.equals(visiturl.getVisitDate())){
                visiturl.setTodayVisitCount(0);
                // 방문 날짜 업데이트
                visiturl.setVisitDate(currentDate);
            }

            // 투데이 방문자 수 증가
            visiturl.setTodayVisitCount(visiturl.getTodayVisitCount() + 1);

            // 방문자 수 업데이트
            visiturlRepository.save(visiturl);
        }
    }

}
