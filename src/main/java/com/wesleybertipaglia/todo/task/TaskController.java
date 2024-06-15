package com.wesleybertipaglia.todo.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wesleybertipaglia.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var currentTime = LocalDateTime.now();
        if (taskModel.getDeadline() != null && currentTime.isAfter(taskModel.getDeadline())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The deadline must be before the current date.");
        }

        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);
        var taskCreated = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        return this.taskRepository.findByUserId((UUID) userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        var task = this.taskRepository.findById(id);

        if (task.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody TaskModel taskModel,
            HttpServletRequest request) {
        var task = this.taskRepository.findById(id).orElse(null);
        var userId = request.getAttribute("userId");

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found.");
        }

        if (!task.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("You cant update this resource.");
        }

        Utils.copyNonNullProperties(taskModel, task);
        this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var taskOptional = this.taskRepository.findById(id);

        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        this.taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Task deleted successfully.");
    }
}
