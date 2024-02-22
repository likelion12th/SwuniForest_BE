package likelion12th.SwuniForest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 공통 매핑 정보가 필요할 때 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
// 공통으로 가지고 있는 엔티티가 아닌 클래스를 상속받을 때 사용
@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
@Getter
public abstract class BaseTime {

    // 등록 시간
    @CreatedDate
    @Column(updatable = false) // 수정 불가
    private LocalDateTime regTime;

    // 수정 시간
    @LastModifiedDate
    private LocalDateTime updateTime;
}
