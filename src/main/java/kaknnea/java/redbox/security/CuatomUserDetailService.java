package kaknnea.java.redbox.security;

import kaknnea.java.redbox.entity.User;
import kaknnea.java.redbox.repositoty.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CuatomUserDetailService implements UserDetailsService {

    // User repository
    private UserRepository userRepository;

    // Constructor Injection
    public CuatomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        // Find user by username or email
        User user = userRepository
                .findByUsernameOrEmail(
                        usernameOrEmail,
                        usernameOrEmail
                )
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not exist by Username or Email"
                        )
                );

        // Convert roles to Spring Security authorities
        Set<GrantedAuthority> authorities =
                user.getRoles()
                        .stream()

                        // Convert Role -> SimpleGrantedAuthority
                        .map(role ->
                                new SimpleGrantedAuthority(
                                        role.getName()
                                )
                        )

                        // Convert stream to Set
                        .collect(Collectors.toSet());

        // Return Spring Security User object
        return new org.springframework.security.core.userdetails.User(

                // Username
                user.getUsername(),

                // Encoded password
                user.getPassword(),

                // Roles / authorities
                authorities
        );
    }
}