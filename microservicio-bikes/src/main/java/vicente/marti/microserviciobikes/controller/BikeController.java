package vicente.marti.microserviciobikes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vicente.marti.microserviciobikes.entity.Bike;
import vicente.marti.microserviciobikes.service.BikeService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/bikes")
@RestController
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(bikeService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Bike> optional = bikeService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optional.get());
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(bikeService.findByUserId(userId));
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Validated @RequestBody Bike bike, BindingResult result) {
        if (result.hasErrors()) return this.validar(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeService.save(bike));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> update(@Validated @RequestBody Bike bike, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) return this.validar(result);
        Optional<Bike> optional = bikeService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        Bike bikeDb = optional.get();
        bikeDb.setBrand(bike.getBrand());
        bikeDb.setModel(bike.getModel());
        bikeDb.setUserId(bike.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeService.save(bikeDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Bike> optional = bikeService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        bikeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors()
                .forEach( error -> errores.put(error.getField(), "El campo ".concat(error.getField().concat(" ").concat(Objects.requireNonNull(error.getDefaultMessage())))));
        return ResponseEntity.badRequest().body(errores);
    }
}
