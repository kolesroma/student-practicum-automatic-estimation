package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.model.MarkEntity;
import com.kpi.kolesnyk.practicum.repository.MarkRepository;
import com.kpi.kolesnyk.practicum.repository.TaskRepository;
import com.kpi.kolesnyk.practicum.repository.UserRepository;
import com.kpi.kolesnyk.practicum.service.MarkService;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.kpi.kolesnyk.practicum.exception.ExceptionSupplier.*;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {
    private final MarkRepository markRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void create(Long taskId, Long userId, Integer score) {
        markRepository.save(MarkEntity.builder()
                .user(userRepository.findById(userId).orElseThrow(USER_NOT_FOUND))
                .task(taskRepository.findById(taskId).orElseThrow(TASK_NOT_FOUND))
                .score(score)
                .build());
    }

    @Override
    @Transactional
    public List<MarkEntity> findAll(Principal principal) {
        var user = userService.findByUsername(principal.getName());
        String authority = user.getRole().getAuthority();
        if ("ROLE_ADMIN".equals(authority)) {
            return markRepository.findAll();
        }
        if ("ROLE_TEACHER".equals(authority)) {
            return user.getTeacherGroups().stream()
                    .flatMap(group -> group.getUsers().stream())
                    .flatMap(student -> markRepository.findAllByUserId(student.getId()).stream())
                    .collect(Collectors.toList());
        }
        return markRepository.findAllByUserId(user.getId());
    }

    @Override
    public List<MarkEntity> findAll(Long userId) {
        return markRepository.findAllByUserId(userId);
    }
}
