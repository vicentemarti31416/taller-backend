package vicente.marti.microserviciojwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vicente.marti.microserviciojwt.dto.CreateUserDto;
import vicente.marti.microserviciojwt.dto.JwtTokenDto;
import vicente.marti.microserviciojwt.dto.LoginUserDto;
import vicente.marti.microserviciojwt.entity.UserEntity;
import vicente.marti.microserviciojwt.enums.RoleEnum;
import vicente.marti.microserviciojwt.exceptions.AttributeException;
import vicente.marti.microserviciojwt.jwt.JwtProvider;
import vicente.marti.microserviciojwt.repository.UserEntityRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UserEntityService {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;


    public UserEntity create(CreateUserDto dto) throws AttributeException {
        if(userEntityRepository.existsByUsername(dto.getUsername())) throw new AttributeException("username already in use");
        if(userEntityRepository.existsByEmail(dto.getEmail())) throw new AttributeException("email already in use");
        if(dto.getRoles().isEmpty()) throw new AttributeException("roles are mandatory");
        return userEntityRepository.save(mapUserFromDto(dto));
    }

    public UserEntity createAdmin(CreateUserDto dto) throws AttributeException {
        if(userEntityRepository.existsByUsername(dto.getUsername())) throw new AttributeException("username already in use");
        if(userEntityRepository.existsByEmail(dto.getEmail())) throw new AttributeException("email already in use");
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        dto.setRoles(roles);
        return userEntityRepository.save(mapUserFromDto(dto));
    }

    public UserEntity createUser(CreateUserDto dto) throws AttributeException {
        if(userEntityRepository.existsByUsername(dto.getUsername())) throw new AttributeException("username already in use");
        if(userEntityRepository.existsByEmail(dto.getEmail())) throw new AttributeException("email already in use");
        List<String> roles = List.of("ROLE_USER");
        dto.setRoles(roles);
        return userEntityRepository.save(mapUserFromDto(dto));
    }

    public JwtTokenDto login(LoginUserDto dto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new JwtTokenDto(token);
    }

    // private methods
    private UserEntity mapUserFromDto(CreateUserDto dto) {
        String password = passwordEncoder.encode(dto.getPassword());
        List<RoleEnum> roles = dto.getRoles().stream().map(rol -> RoleEnum.valueOf(rol)).toList();
        return new UserEntity(dto.getUsername(), dto.getEmail(), password, roles);
    }
}
