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

import java.util.TimeZone;

@RequiredArgsConstructor
@Component
public class DbInit {

    private final MemberRepository memberRepository;
    private final VisitRepository visitRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setDefaultData() {
        // KST 설정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        // 전체 학과 리스트
        String [] majorList = {
                "소프트웨어융합학과", "dep2", "dep3", "dep4", "dep5", "dep6", "dep7", "dep8", "dep9", "dep10",
                "dep11", "dep12", "dep13", "dep14", "dep15", "dep16", "dep17", "dep18", "dep19",
                "dep20", "dep21", "dep22", "dep23", "dep24", "dep25", "dep26", "dep27", "dep28",
                "dep29", "dep30", "dep31", "dep32", "dep33", "dep34", "dep35"
        };

        // 학과별 비밀번호 리스트
        String [] passwordList = {
            "8fnSkODZ", "IqriN419", "VJezcA8O", "b49GAQyt", "VQwse9s2", "lDmYe729", "ND2mBtcz", "7XZO46Js",
            "b5FTmB6k", "BRxo8sou", "MMlxy51k", "pvvsgAMN", "W2Y7qeY1", "0EGI2duF", "XBIvxwTf", "6lrAqPyW",
            "KNZ6clpT", "Tf7AThSJ", "cB4r6QjC", "3lPbdfdg", "TVUY5TP6", "Y1XOvaff", "RVTrr4tn", "HZOsJSJQ",
            "N00F9nG9", "iLIEint5", "Nop88W0M", "aAa7vp4w", "CAkg4oXU", "O7uIo7W5", "9Smt72zZ", "Se9dpdBv",
            "stC1lTlZ", "2KidIg3Q", "3mludySU"
        };

        if (memberRepository.count() == 0) {
            String adminPassword = "likelion1212!!";

            // 최상위 관리자 계정 생성
            Member admin = Member.builder()
                    .studentNum("likelion1212!!")
                    .username("admin")
                    .major("likelion")
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ROLE_ADMIN)
                    .build();

            memberRepository.save(admin);

            for (int i = 0; i < majorList.length; i++) {
                // 학과별 부스 운영진 계정 생성
                Member manager = Member.builder()
                        .studentNum(majorList[i]) // 영문학과명으로 수정
                        .major(majorList[i])
                        .password(passwordEncoder.encode(passwordList[i]))
                        .role(Role.ROLE_MANAGER)
                        .build();

                memberRepository.save(manager);
            }
        }

        if (visitRepository.count() == 0) {
            for (int i = 0; i < majorList.length; i++) {
                // 학과별 방문율 데이터 생성
                Visit visit = Visit.builder()
                        .major(majorList[i])
                        .visitor(0L)
                        .totalStudent(100L)
                        .visitRate(0L)
                        .ranking(0)
                        .build();

                visitRepository.save(visit);

            }
        }
    }
}
