package vicente.marti.microserviciojwt.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vicente.marti.microserviciojwt.dto.*;
import vicente.marti.microserviciojwt.entity.UserEntity;
import vicente.marti.microserviciojwt.exceptions.AttributeException;
import vicente.marti.microserviciojwt.service.UserEntityService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    UserEntityService userEntityService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> create(@Validated @RequestBody CreateUserDto dto) throws AttributeException {
        UserEntity userEntity = userEntityService.create(dto);
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, "user " + userEntity.getUsername() + " has been created"));
    }

    @PostMapping("/create-admin")
    public ResponseEntity<MessageDto> createAdmin(@Validated @RequestBody CreateUserDto dto) throws AttributeException {
        UserEntity userEntity = userEntityService.createAdmin(dto);
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, "admin " + userEntity.getUsername() + " has been created"));
    }

    @PostMapping("/create-user")
    public ResponseEntity<MessageDto> createUser(@Validated @RequestBody CreateUserDto dto) throws AttributeException {
        UserEntity userEntity = userEntityService.createUser(dto);
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, "user " + userEntity.getUsername() + " has been created"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@Validated @RequestBody LoginUserDto dto) throws AttributeException {
        JwtTokenDto jwtTokenDto = userEntityService.login(dto);
        return ResponseEntity.ok(jwtTokenDto);
    }
}
