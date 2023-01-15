package com.kpi.kolesnyk.practicum.service;

import com.kpi.kolesnyk.practicum.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService {
    UserEntity findByUsername(String username);
    void saveUser(UserEntity user);
    List<UserEntity> findAll();
    void ban(Long userId);
}
