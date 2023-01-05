package vicente.marti.microserviciousers.service;

import vicente.marti.microserviciobikes.entity.Bike;
import vicente.marti.microserviciocars.entity.Car;
import vicente.marti.microserviciousers.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    List<Object> findAllVehicles(Long userId);
    Optional<User> findById(Long id);
    User save(User user);
    Car saveCar(Car car);
    Bike saveBike(Bike bike);
    void deleteById(Long id);
}
