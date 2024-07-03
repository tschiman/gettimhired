package com.gettimhired.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class HeaderAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public HeaderAuthenticationFilter(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        super(new AntPathRequestMatcher("/api/**"));
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getHeader("X-GETTIMHIRED-USERNAME");
        String password = request.getHeader("X-GETTIMHIRED-PASSWORD");

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        customAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, chain, authResult);
    }

}
