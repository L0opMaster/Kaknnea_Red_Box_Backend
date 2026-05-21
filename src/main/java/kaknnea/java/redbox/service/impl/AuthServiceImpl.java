package kaknnea.java.redbox.service.impl;

import kaknnea.java.redbox.dto.LoginDtoRequest;
import kaknnea.java.redbox.dto.RegisterDto;
import kaknnea.java.redbox.entity.Role;
import kaknnea.java.redbox.entity.User;
import kaknnea.java.redbox.exception.APIException;
import kaknnea.java.redbox.repositoty.RoleRepository;
import kaknnea.java.redbox.repositoty.UserRepository;
import kaknnea.java.redbox.security.JwtTokenProvider;
import kaknnea.java.redbox.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                           AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String register(RegisterDto registerDto) {

        // check username already exists in db
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // check email already exists in db
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setDateOfBirth(registerDto.getDateOfBirth());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRoles = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role does not exist in database"));
        roles.add(userRoles);
        user.setRoles(roles);
        userRepository.save(user);
        return "User Registered Successfully";
    }

    @Override
    public String login(LoginDtoRequest loginDtoRequest) {

        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                            loginDtoRequest.getUsernameOrEmail(),
                            loginDtoRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}
