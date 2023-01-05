package vicente.marti.microserviciobikes.service;

import vicente.marti.microserviciobikes.entity.Bike;

import java.util.List;
import java.util.Optional;

public interface BikeService {

    List<Bike> findAll();
    Optional<Bike> findById(Long id);
    List<Bike> findByUserId(Long userId);
    Bike save(Bike bike);
    void deleteById(Long id);
}
