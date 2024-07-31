package com.gettimhired.config;

import com.gettimhired.model.dto.CustomUserDetails;
import com.gettimhired.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserService userService;
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        log.info("user is found");
        var userOpt = userService.findUserById(id);
        if (userOpt.isPresent()) {
            log.info("User from user service user={}", userOpt.get());
            return new CustomUserDetails(
                    userOpt.get().id(),
                    userOpt.get().password(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
