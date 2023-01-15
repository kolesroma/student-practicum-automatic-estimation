package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.dto.GroupCreationDto;
import com.kpi.kolesnyk.practicum.dto.QualityCreationDto;
import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.model.*;
import com.kpi.kolesnyk.practicum.repository.*;
import com.kpi.kolesnyk.practicum.service.TaskService;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.kpi.kolesnyk.practicum.exception.ExceptionSupplier.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final FunctionRepository functionRepository;
    private final ParamRepository paramRepository;
    private final CaseRepository caseRepository;
    private final ResultRepository resultRepository;
    private final QualityRepository qualityRepository;
    private final GroupRepository groupRepository;
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
    public void create(TaskCreationDto taskDto, Principal principal) {
        var functionDto = taskDto.getFunction();
        var task = taskRepository.save(TaskEntity.builder()
                .owner(userService.findByUsername(principal.getName()))
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .createdAt(LocalDateTime.now())
                .build());
        groupRepository.findAllById(taskDto.getGroups().stream()
                .map(GroupCreationDto::getId)
                .collect(Collectors.toList()))
                .forEach(groupEntity -> groupEntity.getTasks().add(task));
        var qualityDto = taskDto.getQuality();
        qualityRepository.save(QualityEntity.builder()
                .caseCoef(qualityDto.getCaseCoef())
                .linterCoef(qualityDto.getLinterCoef())
                .complexityCoef(qualityDto.getComplexityCoef())
                .task(task)
                .build());
        var function = functionRepository.save(FunctionEntity.builder()
                .task(task)
                .name(functionDto.getName())
                .build());
        var paramDtos = functionDto.getParams();
        var params = paramDtos.stream()
                .map(paramDto -> ParamEntity.builder()
                        .function(function)
                        .type(paramDto.getType())
                        .name(paramDto.getName())
                        .build())
                .map(paramRepository::save)
                .collect(Collectors.toList());
        functionDto.getInputs().forEach(inputDto -> {
                    var result = resultRepository.save(ResultEntity.builder()
                            .expected(inputDto.getExpectedResult())
                            .build());
                    for (int i = 0; i < params.size(); i++) {
                        caseRepository.save(CaseEntity.builder()
                                .param(params.get(i))
                                .value(inputDto.getInputParamValues().get(i))
                                .result(result)
                                .build());
                    }
                }
        );
    }
}
