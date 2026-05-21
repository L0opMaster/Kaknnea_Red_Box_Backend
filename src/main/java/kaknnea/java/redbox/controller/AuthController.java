package kaknnea.java.redbox.controller;

import kaknnea.java.redbox.dto.JwtAuthResponse;
import kaknnea.java.redbox.dto.LoginDtoRequest;
import kaknnea.java.redbox.dto.RegisterDto;
import kaknnea.java.redbox.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String registerDto1 = authService.register(registerDto);

        return new ResponseEntity<>(registerDto1, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDtoRequest loginDtoRequest){
        String token = authService.login(loginDtoRequest);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
