package likelion12th.SwuniForest.service.visit.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Visit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="visit_id")
    private Long id;

    // 학과명
    private String major;

    // 학과 방문자 수
    private Long visitCount;

    // 학과 총인원
    // private Long totalStudent;

    // 방문률 산출
    // private Long visitRate;

    // 방문율 순위
    private int ranking;


    // 방문률 백분율 계산 메서드
//    public void setVisitRate(Long visitor, Long totalStudent) {
//        if (totalStudent == 0) {
//            this.visitRate = 0L;
//        } else {
//            this.visitRate = (visitor * 100) / totalStudent;
//        }
//    }

    public void addVisitCount() {
        visitCount++;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
