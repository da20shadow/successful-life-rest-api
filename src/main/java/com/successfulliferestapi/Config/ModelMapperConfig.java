package com.successfulliferestapi.Config;

import com.successfulliferestapi.Goal.models.dto.AddGoalDTO;
import com.successfulliferestapi.Goal.models.dto.GoalDTO;
import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Idea.models.dto.IdeaDTO;
import com.successfulliferestapi.Idea.models.entity.Idea;
import com.successfulliferestapi.Idea.models.entity.IdeaTag;
import com.successfulliferestapi.Target.models.dto.TargetDTO;
import com.successfulliferestapi.Target.models.entity.Target;
import com.successfulliferestapi.Task.models.dto.AddTaskDTO;
import com.successfulliferestapi.Task.models.dto.TaskDTO;
import com.successfulliferestapi.Task.models.entity.Task;
import com.successfulliferestapi.Utils.converters.StringToLocalDate;
import com.successfulliferestapi.Utils.converters.StringToLocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    private final StringToLocalDate stringToLocalDateConverter;
    private final StringToLocalDateTime stringToLocalDateTimeConverter;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        addIdeaMappings(modelMapper);
        addGoalMappings(modelMapper);
        goalToGoalDTOMappings(modelMapper);
        targetToTargetDTOMappings(modelMapper);
        addTaskMappings(modelMapper);
        taskToTaskDTOMappings(modelMapper);

        Converter<LocalDateTime, String> localDateTimeToString = new AbstractConverter<LocalDateTime, String>() {
            @Override
            protected String convert(LocalDateTime source) {
                if (source == null) {
                    return null;
                }
                return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        };

        Converter<LocalDate, String> localDateToString = new AbstractConverter<LocalDate, String>() {
            @Override
            protected String convert(LocalDate source) {
                if (source == null) {
                    return null;
                }
                return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        };

        modelMapper.addConverter(localDateTimeToString);
        modelMapper.addConverter(localDateToString);
        return modelMapper;
    }

    private void addIdeaMappings(ModelMapper modelMapper) {
        TypeMap<Idea, IdeaDTO> typeMap = modelMapper.createTypeMap(Idea.class, IdeaDTO.class);
        Converter<Set<IdeaTag>, Set<String>> tagConverter = context -> {
            Set<IdeaTag> source = context.getSource();
            Set<String> target = new HashSet<>();
            for (IdeaTag tag : source) {
                target.add(tag.getName());
            }
            return target;
        };
        typeMap.addMappings(mapper -> mapper.using(tagConverter).map(Idea::getTags, IdeaDTO::setTags));
        // add other mappings specific to Idea and IdeaDTO classes
    }

    private void addGoalMappings(ModelMapper modelMapper) {
        TypeMap<AddGoalDTO, Goal> typeMap =
                modelMapper.createTypeMap(AddGoalDTO.class, Goal.class)
                        .addMappings(mapper -> mapper.using(stringToLocalDateConverter)
                                .map(AddGoalDTO::getDeadline, Goal::setDeadline));

        typeMap.addMappings(mapper -> mapper.skip(Goal::setId));
        // add other mappings specific to AddGoalDTO and Goal classes
    }

    private void goalToGoalDTOMappings(ModelMapper modelMapper) {
        TypeMap<Goal, GoalDTO> typeMap =
                modelMapper.createTypeMap(Goal.class, GoalDTO.class);

        typeMap.addMappings(mapper -> mapper.skip(GoalDTO::setTargets));
        typeMap.addMappings(mapper -> mapper.skip(GoalDTO::setTotalCompletedTargets));
        typeMap.addMappings(mapper -> mapper.skip(GoalDTO::setTotalTargets));
        typeMap.addMappings(mapper -> mapper.skip(GoalDTO::setTotalIdeas));
    }

    private void targetToTargetDTOMappings(ModelMapper modelMapper) {
        TypeMap<Target, TargetDTO> typeMap =
                modelMapper.createTypeMap(Target.class, TargetDTO.class);

        typeMap.addMappings(mapper -> mapper.skip(TargetDTO::setTasks));
        typeMap.addMappings(mapper -> mapper.skip(TargetDTO::setTotalTasks));
        typeMap.addMappings(mapper -> mapper.skip(TargetDTO::setTotalCompletedTasks));
        typeMap.addMappings(mapper -> mapper.skip(TargetDTO::setGoalId));
    }

    private void addTaskMappings(ModelMapper modelMapper) {
        TypeMap<AddTaskDTO, Task> typeMap =
                modelMapper.createTypeMap(AddTaskDTO.class, Task.class)
                        .addMappings(mapper -> mapper.using(stringToLocalDateTimeConverter)
                                .map(AddTaskDTO::getStartDate, Task::setStartDate))
                        .addMappings(mapper -> mapper.using(stringToLocalDateTimeConverter)
                                .map(AddTaskDTO::getDueDate, Task::setDueDate));

        typeMap.addMappings(mapper -> mapper.skip(Task::setId));
        // add other mappings specific to AddTaskDTO and Task classes
    }

    private void taskToTaskDTOMappings(ModelMapper modelMapper) {
        TypeMap<Task, TaskDTO> typeMap =
                modelMapper.createTypeMap(Task.class, TaskDTO.class);

//        typeMap.addMappings(mapper -> mapper.skip(TaskDTO::setTargetId));
        // add other mappings specific to AddTaskDTO and Task classes
    }
}
