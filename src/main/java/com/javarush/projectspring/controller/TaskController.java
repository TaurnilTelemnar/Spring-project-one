package com.javarush.projectspring.controller;

import com.javarush.projectspring.domain.Status;
import com.javarush.projectspring.dto.TaskTo;
import com.javarush.projectspring.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
@SessionAttributes({"currentTask"})
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public ModelAndView showAllTasks(ModelAndView view,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        view.addObject("tasks", taskService.getAllByPage(page, limit));
        view.setViewName("taskPage");
        view.addObject("current_page", page);
        view.addObject("statuses", Status.values());
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            view.addObject("page_numbers", pageNumbers);
        }
        return view;
    }


    @GetMapping("/{id}/")
    public ModelAndView showOneUserAndUsers(ModelAndView view,
                                            @PathVariable("id") Long id,
                                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        TaskTo taskTo = taskService.get(id);
        if (taskTo != null) {
            view.addObject("task", taskTo);
            view.addObject("tasks", taskService.getAllByPage(page, limit));
        }
        view.addObject("current_page", page);
        view.setViewName("taskPage");
        view.addObject("statuses", Status.values());
        return view;
    }

    @PostMapping("/")
    public String createTask(TaskTo taskTo,
                             HttpSession session,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(required = false, name = "createTask") String createTask) {
        session.setAttribute("current_page", page);
        if (Objects.nonNull(createTask)) {
            taskTo = taskService.save(taskTo);
            return "redirect:/tasks/%d/".formatted(taskTo.getId());
        } else {
            session.setAttribute("currentTask", taskTo);
            return "redirect:/tasks/";
        }
    }


    @PostMapping("/{id}/")
    public String updateOrDeleteTask(TaskTo taskTo,

                                     @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, name = "deleteTask") String deleteTask) {

        if (Objects.nonNull(deleteTask)) {
            taskService.delete(taskTo);
            return "redirect:/tasks/";
        } else {
            taskService.save(taskTo);
            return "redirect:/tasks/%d/".formatted(taskTo.getId());
        }
    }

}
