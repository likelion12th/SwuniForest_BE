package likelion12th.SwuniForest.service.lostitem;

import likelion12th.SwuniForest.service.guestbook.domain.Guestbook;
import likelion12th.SwuniForest.service.guestbook.domain.dto.GuestbookDto;
import likelion12th.SwuniForest.service.lostitem.domain.Lostitem;
import likelion12th.SwuniForest.service.lostitem.domain.dto.LostitemDto;
import likelion12th.SwuniForest.service.lostitem.repository.LostitemRepository;
import likelion12th.SwuniForest.service.member.domain.Member;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import likelion12th.SwuniForest.service.visit.VisitService;
import likelion12th.SwuniForest.service.visit.domain.Visit;
import likelion12th.SwuniForest.service.visit.domain.dto.VisitResDto;
import likelion12th.SwuniForest.service.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LostitemService {


    private final LostitemRepository lostitemRepository;

    // 분실물 게시판 글 작성
    @Transactional
    public LostitemDto createLostitem(LostitemDto lostitemDto, String fileName) {

        // 분실물 게시판 글 객체 생성
        Lostitem lostitem = Lostitem.builder()
                .itemTitle(lostitemDto.getItemTitle())
                .findPoint(lostitemDto.getFindPoint())
                .putPoint(lostitemDto.getPutPoint())
                .fileName(fileName)
                .createdAt(LocalDateTime.now()) // 현재 시간 설정
                .build();

        System.out.println("title = " + lostitem.getItemTitle());

        // 데이터베이스에 방명록 저장
        lostitemRepository.save(lostitem);

        // 저장된 방명록의 정보를 guestbookRes(dto)로 변환하여 반환
        LostitemDto lostitemRes = lostitemDto.builder()
                .itemTitle(lostitem.getItemTitle())
                .findPoint(lostitem.getFindPoint())
                .putPoint(lostitem.getPutPoint())
                .fileName(lostitem.getFileName())
                .createdAt(lostitem.getCreatedAt())
                .build();

        return lostitemRes;
    }

    // 분실물 게시판 글 전체 조회
    public List<LostitemDto> getAllLostitem() {
        List<Lostitem> lostitemList = lostitemRepository.findAll();

        // 게시판의 글 정보를 lostitemResList(dto)로 변환하여 반환
        List<LostitemDto> lostitemResList = lostitemList.stream()
                .map(lostitem -> LostitemDto.builder()
                        .itemTitle(lostitem.getItemTitle())
                        .findPoint(lostitem.getFindPoint())
                        .putPoint(lostitem.getPutPoint())
                        .fileName(lostitem.getFileName())
                        .createdAt(lostitem.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return lostitemResList;
    }
}
