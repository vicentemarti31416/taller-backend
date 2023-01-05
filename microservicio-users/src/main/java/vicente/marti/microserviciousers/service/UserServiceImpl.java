package vicente.marti.microserviciousers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vicente.marti.microserviciobikes.entity.Bike;
import vicente.marti.microserviciocars.entity.Car;
import vicente.marti.microserviciousers.entity.User;
import vicente.marti.microserviciousers.feign.BikeFeignClient;
import vicente.marti.microserviciousers.feign.CarFeignClient;
import vicente.marti.microserviciousers.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Object> findAllVehicles(Long userId) {
        List<Bike> bikes = bikeFeignClient.findByUserId(userId);
        List<Car> cars = carFeignClient.findByUserId(userId);
        Stream<Object> stream = Stream.of(bikes, cars).flatMap(Collection::stream);
        return stream.toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Car saveCar(Car car) {
        return carFeignClient.saveCar(car);
    }

    @Override
    public Bike saveBike(Bike bike) {
        return bikeFeignClient.saveBike(bike);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
