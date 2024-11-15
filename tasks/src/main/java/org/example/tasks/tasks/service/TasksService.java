package org.example.tasks.tasks.service;

import lombok.AllArgsConstructor;
import org.example.tasks.errors.exception.BadRequestException;
import org.example.tasks.errors.exception.NotFoundException;
import org.example.tasks.errors.exception.StateConflictException;
import org.example.tasks.tasks.data.Status;
import org.example.tasks.tasks.data.Task;
import org.example.tasks.tasks.request.TaskRequest;
import org.example.tasks.tasks.data.entity.EmployeeEntity;
import org.example.tasks.tasks.data.entity.TaskEntity;
import org.example.tasks.tasks.data.repository.IEmployeeRepository;
import org.example.tasks.tasks.data.repository.ITaskRepository;
import org.example.tasks.utils.TaskMapperFromTaskEntity;
import org.example.tasks.utils.TaskMapperFromTaskRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TasksService {
    private final ITaskRepository taskRepository;
    private final IEmployeeRepository employeeRepository;

    public List<Task> getTasks(int offset, int limit) throws RuntimeException {
        if (limit < 0 || offset < 0) throw new BadRequestException("Wrong value of 'page' or 'limit'");

        return taskRepository.findAll(PageRequest.of(offset, limit)).getContent()
            .stream()
            .map(TaskMapperFromTaskEntity::toTask)
            .toList();
    }


    @Transactional
    public void createTask(TaskRequest taskRequest) throws RuntimeException {
        Task task = TaskMapperFromTaskRequest.toEntity(taskRequest);
        EmployeeEntity author = employeeRepository
            .findById(task.getAuthorId())
            .orElseThrow(() -> new NotFoundException("Author with id: " + task.getAuthorId() + " not found"));

        var mappedTask = TaskMapperFromTaskEntity.toTaskEntity(task, author);
        taskRepository.save(mappedTask);
    }

    @Transactional
    public void assignTask(long taskId, String executorId) throws RuntimeException {
        EmployeeEntity executor = employeeRepository.findById(executorId)
            .orElseThrow(() -> new NotFoundException("Employee with id " + executorId + " not found"));

        TaskEntity task = taskRepository.findById(taskId)
            .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));

        if (task.getStatus() != Status.CREATED) {
            throw new StateConflictException("Task status must be CREATED to assign an executor.");
        }

        task.setExecutorId(executor);
        task.setStatus(Status.IN_PROCESS);
        taskRepository.save(task);
    }

    @Transactional
    public void completeTask(long taskId) throws RuntimeException {
        TaskEntity task = taskRepository.findById(taskId)
            .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));

        if (task.getStatus() != Status.IN_PROCESS) {
            throw new StateConflictException("Task status must be IN_PROCESS to mark it as DONE.");
        }

        task.setStatus(Status.DONE);
        taskRepository.save(task);
    }

    @Transactional
    public void cancelTask(long taskId) throws RuntimeException {
        TaskEntity task = taskRepository.findById(taskId)
            .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));

        if (task.getStatus() != Status.CREATED) {
            throw new StateConflictException("Task can only be canceled if it is in CREATED status.");
        }

        task.setStatus(Status.CANCELED);
        taskRepository.save(task);
    }
}
