package vicente.marti.microserviciousers.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vicente.marti.microserviciobikes.entity.Bike;

import java.util.List;

@FeignClient(name = "microservicio-bikes")
public interface BikeFeignClient {

    @PostMapping("/bikes/")
    Bike saveBike(@Validated @RequestBody Bike bike);

    @GetMapping("/bikes/userId/{userId}")
    List<Bike> findByUserId(@PathVariable Long userId);
}
