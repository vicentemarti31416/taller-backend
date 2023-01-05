package vicente.marti.microserviciousers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vicente.marti.microserviciobikes.entity.Bike;
import vicente.marti.microserviciocars.entity.Car;
import vicente.marti.microserviciousers.entity.User;
import vicente.marti.microserviciousers.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/vehicles/{userId}")
    public ResponseEntity<?> findAllVehicles(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findAllVehicles(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> optional = userService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optional.get());
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Validated @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) return this.validar(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping("/car/{userId}")
    public ResponseEntity<?> saveCar(@Validated @RequestBody Car car, BindingResult result, @PathVariable Long userId) {
        if (userService.findById(userId).isEmpty()) return ResponseEntity.notFound().build();
        if (result.hasErrors()) return this.validar(result);
        car.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveCar(car));
    }

    @PostMapping("/bike/{userId}")
    public ResponseEntity<?> saveCar(@Validated @RequestBody Bike bike, BindingResult result, @PathVariable Long userId) {
        if (userService.findById(userId).isEmpty()) return ResponseEntity.notFound().build();
        if (result.hasErrors()) return this.validar(result);
        bike.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveBike(bike));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> update(@Validated @RequestBody User user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) return this.validar(result);
        Optional<User> optional = userService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        User userDb = optional.get();
        userDb.setName(user.getName());
        userDb.setEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<User> optional = userService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors()
                .forEach( error -> errores.put(error.getField(), "El campo ".concat(error.getField().concat(" ").concat(Objects.requireNonNull(error.getDefaultMessage())))));
        return ResponseEntity.badRequest().body(errores);
    }

}
