package likelion12th.SwuniForest.service.stamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SchedulerRunner implements CommandLineRunner {

    @Autowired
    private CertificationService certificationService;

    @Override
    public void run(String... args) throws Exception {
        certificationService.scheduledUpdateCertificationCodes();
    }
}
