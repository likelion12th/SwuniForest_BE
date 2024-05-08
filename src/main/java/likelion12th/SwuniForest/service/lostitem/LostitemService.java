package likelion12th.SwuniForest.service.lostitem;

import likelion12th.SwuniForest.service.lostitem.domain.Lostitem;
import likelion12th.SwuniForest.service.lostitem.domain.dto.LostitemDto;
import likelion12th.SwuniForest.service.lostitem.repository.LostitemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
                .build();

        return lostitemRes;
    }
}
