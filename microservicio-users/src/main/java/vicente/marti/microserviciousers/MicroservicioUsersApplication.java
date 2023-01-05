package vicente.marti.microserviciousers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioUsersApplication.class, args);
	}

}
