package likelion12th.SwuniForest.service.visiturl.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Visiturl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="visiturl_id")
    private Long id;

    // 누적 방문자 수
    private int totalVisitCount = 0;

    // 오늘 방문자 수
    private int todayVisitCount = 0;

    // 방문 날짜
    private LocalDate visitDate = LocalDate.now();

    public void setTotalVisitCount(int totalVisitCount) {
        this.totalVisitCount = totalVisitCount;
    }

    public void setTodayVisitCount(int todayVisitCount) {
        this.todayVisitCount = todayVisitCount;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }
}
