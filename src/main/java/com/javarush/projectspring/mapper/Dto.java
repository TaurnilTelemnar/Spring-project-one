package com.javarush.projectspring.mapper;

import com.javarush.projectspring.domain.Task;
import com.javarush.projectspring.dto.TaskTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Dto {

    Dto MAPPER = Mappers.getMapper(Dto.class);

    TaskTo from(Task task);
    Task from(TaskTo taskTo);
}
