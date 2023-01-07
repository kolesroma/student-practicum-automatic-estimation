package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.UserDetailsImpl;
import com.kpi.kolesnyk.practicum.model.UserEntity;
import com.kpi.kolesnyk.practicum.repository.UserRepository;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new UserDetailsImpl(userRepository.findByUsername(username)
                .orElseThrow());
    }

    @Override
    @Transactional
    public void saveUser(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException();
        }
        userRepository.save(user);
    }
}
