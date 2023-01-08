package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.service.impl.details.UserDetailsImpl;
import com.kpi.kolesnyk.practicum.model.UserEntity;
import com.kpi.kolesnyk.practicum.repository.UserRepository;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kpi.kolesnyk.practicum.exception.ExceptionSupplier.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = findByUsername(username);
        log.info("logged in: " + user);
        return new UserDetailsImpl(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(USER_NOT_FOUND);
    }

    @Override
    @Transactional
    public void saveUser(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            USER_ALREADY_REGISTERED.get();
        }
        userRepository.save(user);
    }
}
