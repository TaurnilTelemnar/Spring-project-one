package com.javarush.projectspring.dto;

import com.javarush.projectspring.domain.Status;
import lombok.Data;

@Data
public class TaskTo {

    Long id;

    String description;

    Status status;
}
