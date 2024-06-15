package com.wesleybertipaglia.todo.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wesleybertipaglia.todo.user.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Basic ")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing or invalid Authorization header.");
            return;
        }

        try {
            String authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            String auth = new String(authDecoded);

            String[] credentials = auth.split(":");
            if (credentials.length != 2) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid Authorization format.");
                return;
            }

            String username = credentials[0];
            String password = credentials[1];

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "User or password invalid.");
                return;
            }

            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (passwordVerify.verified) {
                request.setAttribute("userId", user.getId());
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "User or password invalid.");
            }
        } catch (IllegalArgumentException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Failed to decode credentials.");
        }
    }
}
