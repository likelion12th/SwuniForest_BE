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
    private Long visitor;

    // 학과 총인원
    private Long totalStudent;

    // 방문률 산출
    private Long visitRate;

    // 생성자 추가
    // 모든 필드를 매개변수로 받는 생성자
    public Visit (String major, Long visitor, Long totalStudent) {
        this.major = major;
        this.visitor = visitor;
        this.totalStudent = totalStudent;
        this.visitRate = calculateVisitRate(visitor, totalStudent);
    }

    // 방문률 계산 메서드
    private Long calculateVisitRate(Long visitor, Long totalStudent) {
        if (totalStudent == 0) {
            return 0L; // 분모가 0이면 방문률을 0으로 설정
        }
        return (visitor * 100) / totalStudent;
    }

    public void addVisitor() {
        visitor++;
    }
}
