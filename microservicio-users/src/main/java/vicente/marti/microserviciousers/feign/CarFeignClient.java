package vicente.marti.microserviciousers.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vicente.marti.microserviciocars.entity.Car;

import java.util.List;

@FeignClient(name = "microservicio-cars", url = "http://localhost:8002")
public interface CarFeignClient {

    @PostMapping("/cars/")
    Car saveCar(@Validated @RequestBody Car car);

    @GetMapping("/cars/userId/{userId}")
    List<Car> findByUserId(@PathVariable Long userId);
}
