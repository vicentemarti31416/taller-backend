package vicente.marti.microserviciocars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vicente.marti.microserviciocars.entity.Car;
import vicente.marti.microserviciocars.service.CarService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/cars")
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Car> optional = carService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optional.get());
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(carService.findByUserId(userId));
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Validated @RequestBody Car car, BindingResult result) {
        if (result.hasErrors()) return this.validar(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(car));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> update(@Validated @RequestBody Car car, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) return this.validar(result);
        Optional<Car> optional = carService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        Car carDb = optional.get();
        carDb.setBrand(car.getBrand());
        carDb.setModel(car.getModel());
        carDb.setUserId(car.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(carDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Car> optional = carService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        carService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors()
                .forEach( error -> errores.put(error.getField(), "El campo ".concat(error.getField().concat(" ").concat(Objects.requireNonNull(error.getDefaultMessage())))));
        return ResponseEntity.badRequest().body(errores);
    }
}
