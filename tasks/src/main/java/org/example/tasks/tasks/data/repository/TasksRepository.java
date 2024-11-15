package org.example.tasks.tasks.data.repository;

import lombok.AllArgsConstructor;
import org.example.tasks.errors.exception.NotFoundException;
import org.example.tasks.errors.exception.StateConflictException;
import org.example.tasks.tasks.data.Status;
import org.example.tasks.tasks.data.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TasksRepository {

    private final List<Task> tasks = new ArrayList<>();
    private final List<String> employees = new ArrayList<>();

    {
        // Инициализация задач
        tasks.add(new Task(1L, "Task 1", "Description 1", "2024-11-14", "author1", null, Status.CREATED));
        tasks.add(new Task(2L, "Task 2", "Description 2", "2024-11-14", "author2", null, Status.CREATED));
        tasks.add(new Task(3L, "Task 3", "Description 3", "2024-11-14", "author3", null, Status.CREATED));

        // Инициализация списка сотрудников
        employees.add("executor1");
        employees.add("executor2");
        employees.add("executor3");
    }

    public List<Task> getTasks(final int offset, final int limit) {
        return tasks.stream().skip(offset).limit(limit).toList();
    }

    public void createTask(Task task) {
        employees.stream().filter(id -> id.equals(task.getAuthorId())).findFirst().orElseThrow(() -> new NotFoundException("Author not found"));
        tasks.add(task);
    }

    public void assignTask(long taskId, String executorId) throws RuntimeException {
        if (!employees.contains(executorId)) {
            throw new NotFoundException("Employee with id " + executorId + " not found.");
        }

        Task task = findTaskById(taskId);
        if (task.getStatus() != Status.CREATED) {
            throw new StateConflictException("Task status must be CREATED to assign an executor.");
        }

        task.setStatus(Status.IN_PROCESS);
        task.setExecutorId(executorId);
    }

    public void completeTask(long taskId) throws RuntimeException {
        Task task = findTaskById(taskId);
        if (task.getStatus() != Status.IN_PROCESS) {
            throw new StateConflictException("Task status must be IN_PROCESS to mark it as DONE.");
        }

        task.setStatus(Status.DONE);
    }

    public void cancelTask(long taskId) throws RuntimeException {
        Task task = findTaskById(taskId);
        if (task.getStatus() != Status.CREATED) {
            throw new StateConflictException("Task can only be canceled if it is in CREATED status.");
        }

        task.setStatus(Status.CANCELED);
    }

    private Task findTaskById(long taskId) throws RuntimeException {
        return tasks.stream()
            .filter(task -> task.getTaskId() == taskId)
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found."));
    }
}
