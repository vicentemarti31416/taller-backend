package vicente.marti.microserviciocars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioCarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioCarsApplication.class, args);
	}

}
