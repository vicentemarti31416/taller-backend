package vicente.marti.microserviciocars.service;

import vicente.marti.microserviciocars.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> findAll();
    Optional<Car> findById(Long id);
    List<Car> findByUserId(Long userId);
    Car save(Car car);
    void deleteById(Long id);
}
