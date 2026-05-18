package kaknnea.java.redbox.service;

import kaknnea.java.redbox.dto.LoginDtoRequest;
import kaknnea.java.redbox.dto.RegisterDto;
import kaknnea.java.redbox.repositoty.UserRepository;

public interface AuthService {

    String register (RegisterDto registerDto);

    String login (LoginDtoRequest loginDtoRequest);
}
