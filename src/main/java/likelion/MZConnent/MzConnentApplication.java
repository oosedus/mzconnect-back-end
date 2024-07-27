package likelion.MZConnent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MzConnentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MzConnentApplication.class, args);
	}

}
