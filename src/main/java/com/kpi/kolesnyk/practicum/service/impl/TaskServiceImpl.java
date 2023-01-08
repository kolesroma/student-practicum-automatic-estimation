package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.model.*;
import com.kpi.kolesnyk.practicum.repository.CaseRepository;
import com.kpi.kolesnyk.practicum.repository.FunctionRepository;
import com.kpi.kolesnyk.practicum.repository.ParamRepository;
import com.kpi.kolesnyk.practicum.repository.ResultRepository;
import com.kpi.kolesnyk.practicum.repository.TaskRepository;
import com.kpi.kolesnyk.practicum.service.TaskService;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static com.kpi.kolesnyk.practicum.exception.ExceptionSupplier.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final FunctionRepository functionRepository;
    private final ParamRepository paramRepository;
    private final CaseRepository caseRepository;
    private final ResultRepository resultRepository;
    private final UserService userService;

    @Override
    @Transactional
    public List<TaskEntity> findAll(Principal principal) {
        var user = userService.findByUsername(principal.getName());
        if ("ROLE_ADMIN".equals(user.getRole().getAuthority())) {
            return taskRepository.findAll();
        }
        var group = user.getGroup();
        if (group == null) {
            return taskRepository.findAllByOwnerId(user.getId());
        }
        return group.getTasks();
    }

    @Override
    public TaskEntity findById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(TASK_NOT_FOUND);
    }

    @Override
    @Transactional
    public void create(TaskCreationDto taskDto) {
        var functionDto = taskDto.getFunction();
        var function = functionRepository.save(FunctionEntity.builder()
                .task(taskRepository.save(TaskEntity.builder()
                        .name(taskDto.getName())
                        .description(taskDto.getDescription())
                        .createdAt(LocalDateTime.now())
                        .build()))
                .name(functionDto.getName())
                .build());
        var paramDtos = functionDto.getParams();
        functionDto.getInputs().forEach(inputDto -> {
                    var result = resultRepository.save(ResultEntity.builder()
                            .expected(inputDto.getExpectedResult())
                            .build());
                    for (int i = 0; i < paramDtos.size(); i++) {
                        var paramDto = paramDtos.get(i);
                        var param = paramRepository.save(ParamEntity.builder()
                                .function(function)
                                .type(paramDto.getType())
                                .name(paramDto.getName())
                                .build());
                        caseRepository.save(CaseEntity.builder()
                                .param(param)
                                .value(inputDto.getInputParamValues().get(i))
                                .result(result)
                                .build());
                    }
                }
        );
    }
}
