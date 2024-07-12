package com.gettimhired.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new UsernamePasswordAuthenticationToken(null, null, List.of("USER").stream().map(SimpleGrantedAuthority::new).toList());
    }
}
