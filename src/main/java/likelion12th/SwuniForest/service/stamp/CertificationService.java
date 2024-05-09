package likelion12th.SwuniForest.service.stamp;

import likelion12th.SwuniForest.service.stamp.domain.Certification;
import likelion12th.SwuniForest.service.stamp.domain.dto.CertificationDto;
import likelion12th.SwuniForest.service.stamp.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationService {
    @Autowired
    private CertificationRepository certificationRepository;

    // 초기 인증번호 생성
    public void initCertificationCodes() {
        List<Certification> certifications = certificationRepository.findAll();
        if (certifications.isEmpty()) {
            for (int i = 1; i <= 35; i++) {
                Certification certification = new Certification();
                certification.setDepartment("Department" + i);
                certification.setCode(generateNewCode());
                certificationRepository.save(certification);
            }
        }
    }

    // 5분마다 인증번호 갱신
    @Scheduled(fixedRate = 300000)
    public void scheduledUpdateCertificationCodes() {
        List<Certification> certifications = certificationRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        for (Certification certification : certifications) {
            LocalDateTime nextExpirationTime = now.plusMinutes(5); // 5분 후
            String formattedExpirationTime = nextExpirationTime.format(formatter);
            certification.setCode(generateNewCode());
            certification.setExpirationTime(formattedExpirationTime);
            certificationRepository.save(certification);
        }
    }


    // 새로운 인증번호 생성
    private String generateNewCode() {
        // 랜덤한 인증번호
        String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6); // 6자리 제한
        return code;
    }

    // 모든 코드 리스트 반환
    @Transactional
    public List<CertificationDto> getCodeList() {
        List<Certification> certificationList = certificationRepository.findAll();
        return certificationList.stream()
                .map(certification -> CertificationDto.builder()
                        .department(certification.getDepartment())
                        .code(certification.getCode())
                        .expirationTime(certification.getExpirationTime())
                        .build())
                .collect(Collectors.toList());
    }

    // 학과별 코드 반환
    @Transactional
    public CertificationDto getDepCode(Long depNumber){
        Optional<Certification> optionalCertification = certificationRepository.findById(depNumber);
        if (optionalCertification.isPresent()){
            Certification certification = optionalCertification.get();
            return CertificationDto.builder()
                    .department(certification.getDepartment())
                    .code(certification.getCode())
                    .expirationTime(certification.getExpirationTime())
                    .build();
        }
        else{
            throw new RuntimeException("유효하지 않은 학과번호입니다.");
        }

    }

}
