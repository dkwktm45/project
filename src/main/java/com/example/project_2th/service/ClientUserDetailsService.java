package com.example.project_2th.service;

import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class ClientUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginNumber) throws UsernameNotFoundException {
        User user = userRepository.findByLoginNumber(loginNumber);
        if(user == null){
            throw new UsernameNotFoundException("loginNumberNotFoundException");
        }
        log.info(String.valueOf(user));
        return user;
    }
}
