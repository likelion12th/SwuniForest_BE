package likelion12th.SwuniForest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄러 설정
@SpringBootApplication
public class SwuniForestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwuniForestApplication.class, args);
	}

}
