package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.GroupEntity;
import com.kpi.kolesnyk.practicum.model.RoleEntity;
import com.kpi.kolesnyk.practicum.repository.GroupRepository;
import com.kpi.kolesnyk.practicum.service.impl.details.UserDetailsImpl;
import com.kpi.kolesnyk.practicum.model.UserEntity;
import com.kpi.kolesnyk.practicum.repository.UserRepository;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

import static com.kpi.kolesnyk.practicum.exception.ExceptionSupplier.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        user.setRole(RoleEntity.builder().id(1L).build());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setBirthday(LocalDate.of(2002, Month.FEBRUARY,2));
        String groupName = user.getGroup().getDescription();
        var groupDB = groupRepository.getByDescription(groupName);
        if (groupDB == null) {
            user.setGroup(groupRepository.save(GroupEntity.builder().description(groupName).build()));
        } else {
            user.setGroup(groupDB);
        }
        userRepository.save(user);
    }
}
