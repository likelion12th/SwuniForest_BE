package likelion12th.SwuniForest.service.stamp;

import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.service.member.repository.MemberRepository;
import likelion12th.SwuniForest.service.stamp.domain.Certification;
import likelion12th.SwuniForest.service.stamp.domain.Stamp;
import likelion12th.SwuniForest.service.stamp.domain.dto.StampDto;
import likelion12th.SwuniForest.service.stamp.repository.CertificationRepository;
import likelion12th.SwuniForest.service.stamp.repository.StampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final CertificationRepository certificationRepository;


    @Transactional
    public void checkStamp(String depCode) {
        /*

        -- 현재 로그인중인 유저 (Member) 가져와야함 ( 스템프 아이디 대응 )  --
        Stamp stamp = stampRepository.findByMember(member)
         */
        // 우선 1번만
        Stamp stamp = stampRepository.findById(1L).orElseThrow(() -> new RuntimeException("스템프를 찾을 수 없습니다."));
        // 학과 코드로 학과 조회
        if(certificationRepository.findByCode(depCode).isPresent()){
            Certification certification = certificationRepository.findByCode(depCode).get();
        }
        else{
            throw new IllegalArgumentException("유효하지 않은 인증번호 입니다. ");
        }

        int depNumber = certificationRepository.findByCode(depCode).get().getId().intValue();

        switch (depNumber) {
            case 1:
                stamp.setDep1Checked(true);
                break;
            case 2:
                stamp.setDep2Checked(true);
                break;
            case 3:
                stamp.setDep3Checked(true);
                break;
            case 4:
                stamp.setDep4Checked(true);
                break;
            case 5:
                stamp.setDep5Checked(true);
                break;
            case 6:
                stamp.setDep6Checked(true);
                break;
            case 7:
                stamp.setDep7Checked(true);
                break;
            case 8:
                stamp.setDep8Checked(true);
                break;
            case 9:
                stamp.setDep9Checked(true);
                break;
            case 10:
                stamp.setDep10Checked(true);
                break;
            case 11:
                stamp.setDep11Checked(true);
                break;
            case 12:
                stamp.setDep12Checked(true);
                break;
            case 13:
                stamp.setDep13Checked(true);
                break;
            case 14:
                stamp.setDep14Checked(true);
                break;
            case 15:
                stamp.setDep15Checked(true);
                break;
            case 16:
                stamp.setDep16Checked(true);
                break;
            case 17:
                stamp.setDep17Checked(true);
                break;
            case 18:
                stamp.setDep18Checked(true);
                break;
            case 19:
                stamp.setDep19Checked(true);
                break;
            case 20:
                stamp.setDep20Checked(true);
                break;
            case 21:
                stamp.setDep21Checked(true);
                break;
            case 22:
                stamp.setDep22Checked(true);
                break;
            case 23:
                stamp.setDep23Checked(true);
                break;
            case 24:
                stamp.setDep24Checked(true);
                break;
            case 25:
                stamp.setDep25Checked(true);
                break;
            case 26:
                stamp.setDep26Checked(true);
                break;
            case 27:
                stamp.setDep27Checked(true);
                break;
            case 28:
                stamp.setDep28Checked(true);
                break;
            case 29:
                stamp.setDep29Checked(true);
                break;
            case 30:
                stamp.setDep30Checked(true);
                break;
            case 31:
                stamp.setDep31Checked(true);
                break;
            case 32:
                stamp.setDep32Checked(true);
                break;
            case 33:
                stamp.setDep33Checked(true);
                break;
            case 34:
                stamp.setDep34Checked(true);
                break;
            case 35:
                stamp.setDep35Checked(true);
                break;
            default:
                throw new IllegalArgumentException("유효하지 않은 학과 코드 :  " + depNumber);
        }

        stampRepository.save(stamp);

    }

    @Transactional
    public StampDto stampBoard(){

         /*

        -- 현재 로그인중인 유저 (Member) 가져와야함 ( 스템프 아이디 대응 )  --
        Stamp stamp = stampRepository.findByMember(member)
         */
        // 우선 1번만
        Stamp stamp = stampRepository.findById(1L).orElseThrow(() -> new RuntimeException("스템프를 찾을 수 없습니다."));
        // entity -> dto 변환
        StampDto stampDto = StampDto.builder()
                .dep1Checked(stamp.isDep1Checked())
                .dep2Checked(stamp.isDep2Checked())
                .dep3Checked(stamp.isDep3Checked())
                .dep4Checked(stamp.isDep4Checked())
                .dep5Checked(stamp.isDep5Checked())
                .dep6Checked(stamp.isDep6Checked())
                .dep7Checked(stamp.isDep7Checked())
                .dep8Checked(stamp.isDep8Checked())
                .dep9Checked(stamp.isDep9Checked())
                .dep10Checked(stamp.isDep10Checked())
                .dep11Checked(stamp.isDep11Checked())
                .dep12Checked(stamp.isDep12Checked())
                .dep13Checked(stamp.isDep13Checked())
                .dep14Checked(stamp.isDep14Checked())
                .dep15Checked(stamp.isDep15Checked())
                .dep16Checked(stamp.isDep16Checked())
                .dep17Checked(stamp.isDep17Checked())
                .dep18Checked(stamp.isDep18Checked())
                .dep19Checked(stamp.isDep19Checked())
                .dep20Checked(stamp.isDep20Checked())
                .dep21Checked(stamp.isDep21Checked())
                .dep22Checked(stamp.isDep22Checked())
                .dep23Checked(stamp.isDep23Checked())
                .dep24Checked(stamp.isDep24Checked())
                .dep25Checked(stamp.isDep25Checked())
                .dep26Checked(stamp.isDep26Checked())
                .dep27Checked(stamp.isDep27Checked())
                .dep28Checked(stamp.isDep28Checked())
                .dep29Checked(stamp.isDep29Checked())
                .dep30Checked(stamp.isDep30Checked())
                .build();
        return stampDto;
    }
}
