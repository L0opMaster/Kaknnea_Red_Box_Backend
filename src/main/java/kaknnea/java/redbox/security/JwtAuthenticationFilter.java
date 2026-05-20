package kaknnea.java.redbox.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Filter runs once for every request
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JWT utility class
    private final JwtTokenProvider jwtTokenProvider;

    // Service to load user from database
    private final UserDetailsService userDetailsService;

    // Constructor Injection
    public JwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    // Get JWT token from request header
    public String getTokenFromRequest(HttpServletRequest request) {

        // Read Authorization header
        String bearerToken = request.getHeader("Authorization");

        // Check header exists and starts with "Bearer "
        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith("Bearer ")) {

            // Remove "Bearer " and return token
            return bearerToken.substring(7, bearerToken.length());
        }

        // No token found
        return null;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Get token from request
        String token = getTokenFromRequest(request);

        // Check token exists and valid
        if (StringUtils.hasText(token)
                && jwtTokenProvider.validateToken(token)) {

            // Get username from token
            String username =
                    jwtTokenProvider.getUsernameFromToken(token);

            // Load user details from database
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            // Create authentication object
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,                  // current user
                            null,                         // password not needed
                            userDetails.getAuthorities()  // roles
                    );

            // Add request details
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            // Save authenticated user into SecurityContext
            SecurityContextHolder.getContext()
                    .setAuthentication(authenticationToken);
        }

        // Continue request
        filterChain.doFilter(request, response);
    }
}