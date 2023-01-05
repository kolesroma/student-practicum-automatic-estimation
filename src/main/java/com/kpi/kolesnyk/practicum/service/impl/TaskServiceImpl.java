package com.kpi.kolesnyk.practicum.service.impl;

import com.kpi.kolesnyk.practicum.dto.TaskCreationDto;
import com.kpi.kolesnyk.practicum.model.CaseEntity;
import com.kpi.kolesnyk.practicum.model.FunctionEntity;
import com.kpi.kolesnyk.practicum.model.ParamEntity;
import com.kpi.kolesnyk.practicum.model.ResultEntity;
import com.kpi.kolesnyk.practicum.model.TaskEntity;
import com.kpi.kolesnyk.practicum.repository.CaseRepository;
import com.kpi.kolesnyk.practicum.repository.FunctionRepository;
import com.kpi.kolesnyk.practicum.repository.ParamRepository;
import com.kpi.kolesnyk.practicum.repository.ResultRepository;
import com.kpi.kolesnyk.practicum.repository.TaskRepository;
import com.kpi.kolesnyk.practicum.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final FunctionRepository functionRepository;
    private final ParamRepository paramRepository;
    private final CaseRepository caseRepository;
    private final ResultRepository resultRepository;

    @Override
    public List<TaskEntity> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskEntity findById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow();
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
