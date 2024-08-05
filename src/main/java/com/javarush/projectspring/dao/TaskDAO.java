package com.javarush.projectspring.dao;

import com.javarush.projectspring.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDAO extends JpaRepository<Task, Long> {

}
