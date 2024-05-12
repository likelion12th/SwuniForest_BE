package likelion12th.SwuniForest.service.guestbook;

import likelion12th.SwuniForest.service.guestbook.domain.Guestbook;
import likelion12th.SwuniForest.service.guestbook.domain.dto.GuestbookDto;
import likelion12th.SwuniForest.service.guestbook.repository.GuestbookRepository;
import likelion12th.SwuniForest.service.member.MemberService;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.Role;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import likelion12th.SwuniForest.service.visit.VisitService;
import likelion12th.SwuniForest.service.visit.domain.Visit;
import likelion12th.SwuniForest.service.visit.domain.dto.VisitResDto;
import likelion12th.SwuniForest.service.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final VisitRepository visitRepository;
    private final VisitService visitService;

    // 방명록 작성
    @Transactional
    public GuestbookDto createGuestbook(GuestbookDto guestbookDto, String fileName) {

        // 클라이언트로부터 전송된 요청에서 익명 여부를 확인
        boolean isAnonymous = guestbookDto.isAnonymous();


        // 사용자가 익명으로 글을 작성한 경우, 이름을 "익명"으로 설정
        String guestName;
        if (isAnonymous) {
            guestName = "익명";
        } else {
            // 로그인한 사용자의 정보 -> guestName
            MemberResDto memberResDto = memberService.getMyMemberInfo();
            if (memberResDto != null) {
                guestName = memberResDto.getUsername();
            } else {
                // 로그인이 필요한 경우 메시지 반환
                throw new RuntimeException("로그인이 필요합니다.");
            }
        }

        // 방명록 객체 생성
        Guestbook guestbook = Guestbook.builder()
                .guestName(guestName)
                .guestContent(guestbookDto.getGuestContent())
                .fileName(fileName)
                .anonymous(guestbookDto.isAnonymous())
                .build();

        System.out.println("name = " + guestbook.getGuestName());
        System.out.println("content = " + guestbook.getGuestContent());

        // 데이터베이스에 방명록 저장
        guestbookRepository.save(guestbook);

        // 저장된 방명록의 정보를 guestbookRes(dto)로 변환하여 반환
        GuestbookDto guestbookRes = GuestbookDto.builder()
                .guestName(guestbook.getGuestName())
                .guestContent(guestbook.getGuestContent())
                .fileName(guestbook.getFileName())
                .anonymous(guestbook.isAnonymous())
                .build();

        return guestbookRes;
    }

    public List<GuestbookDto> getAllGuestbook() {
        List<Guestbook> guestbookList = guestbookRepository.findAll();

        // 저장된 방명록의 정보를 guestbookResList(dto)로 변환하여 반환
        List<GuestbookDto> guestbookResList = guestbookList.stream()
                .map(guestbook -> GuestbookDto.builder()
                        .guestName(guestbook.getGuestName())
                        .guestContent(guestbook.getGuestContent())
                        .fileName(guestbook.getFileName())
                        .anonymous(guestbook.isAnonymous())
                        .build())
                .collect(Collectors.toList());

        return guestbookResList;
    }

}
