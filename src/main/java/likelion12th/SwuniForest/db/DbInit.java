package likelion12th.SwuniForest.db;

import jakarta.annotation.PostConstruct;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.Role;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import likelion12th.SwuniForest.service.visit.domain.Visit;
import likelion12th.SwuniForest.service.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@RequiredArgsConstructor
@Component
public class DbInit {

    private final MemberRepository memberRepository;
    private final VisitRepository visitRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setDefaultData() {
        // 전체 학과 리스트
        String [] majorList = {
                "dep1", "dep2", "dep3", "dep4", "dep5", "dep6", "dep7", "dep8", "dep9", "dep10",
                "dep11", "dep12", "dep13", "dep14", "dep15", "dep16", "dep17", "dep18", "dep19",
                "dep20", "dep21", "dep22", "dep23", "dep24", "dep25", "dep26", "dep27", "dep28",
                "dep29", "dep30", "dep31", "dep32", "dep33", "dep34", "dep35"
        };

        // 폼으로 입력하는게 아니라서.. 미리 선언
        String adminPassword = "likelion1212!!";

        // 최상위 관리자 계정 생성
        Member admin = Member.builder()
                .studentNum("2021111412") // 우선 내 학번으로 했음
                .username("admin")
                .major("likelion12th")
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ROLE_ADMIN)
                .build();

        memberRepository.save(admin);

        for (int i = 0; i < majorList.length; i++) {
            // 학과별 방문율 데이터 생성
            Visit visit = Visit.builder()
                    .major(majorList[i])
                    .visitor(0L)
                    .totalStudent(100L)
                    .visitRate(0L)
                    .build();

            visitRepository.save(visit);

            // 학과별 부스 운영진 계정 생성
            Member manager = Member.builder()
                    .studentNum(majorList[i]) // 영문학과명 + 숫자로 수정
                    .major(majorList[i])
                    .password(generateRandomString(8))
                    .role(Role.ROLE_MANAGER)
                    .build();

            memberRepository.save(manager);
        }
    }

    // 랜덤 스트링 생성 메소드
    private String generateRandomString(int length) {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }


}
