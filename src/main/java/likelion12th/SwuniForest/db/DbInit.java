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

        // 전체 학과 아이디 리스트
        String [] loginIdList = {
                "dep1", "dep2", "dep3", "dep4", "dep5", "dep6", "dep7", "dep8", "dep9", "dep10",
                "dep11", "dep12", "dep13", "dep14", "dep15", "dep16", "dep17", "dep18", "dep19",
                "dep20", "dep21", "dep22", "dep23", "dep24", "dep25", "dep26", "dep27", "dep28",
                "dep29", "dep30", "dep31", "dep32", "dep33", "dep34", "dep35", "dep36", "dep37",
                "dep38", "dep39", "dep40", "dep41", "dep42",
                "dep43", "dep44", "dep45", "dep46", "dep47",
                "dep48", "dep49"
        };

        String [] majorList = {
                "경제학과", "문헌정보학과", "사회복지학과", "아동학과", "행정학과",
                "언론영상학부", "교육심리학과", "스포츠운동과학과", "현대미술전공", "공예전공",
                "시각디자인전공", "첨단미디어디자인전공", "경영학과", "패션산업학과", "디지털미디어학과",
                "정보보호학부", "소프트웨어융합학과", "데이터사이언스학과", "산업디자인학과", "글로벌ICT인문융합학부",
                "국어국문학과", "영어영문학과", "중어중문학과", "일어일문학과", "사학과",
                "기독교학과", "자율전공학부", "수학과", "화학과", "생명환경공학과",
                "바이오헬스융합학과", "원예생명조경학과", "식품공학과", "식품영양학과", "불어불문학과",
                "독어독문학과", "화학전공", "생명환경공학전공", "정보보호학과", "메타버융합콘텐츠전공",
                "프랑스문화콘텐츠전공", "독일문화콘텐츠전공", "저널리즘전공", "비즈니스커뮤니케이션전공", "디지털영상전공",
                "식품공학전공", "식품영양학전공", "사이버보안전공", "개인정보보호전공"
        };

        // 학과별 비밀번호 리스트 (dep34까지만)
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
                        .studentNum(loginIdList[i]) // 영문학과명으로 수정
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
                        .visitCount(0L)
                        .ranking(0)
                        .build();

                visitRepository.save(visit);

            }
        }
    }
}
