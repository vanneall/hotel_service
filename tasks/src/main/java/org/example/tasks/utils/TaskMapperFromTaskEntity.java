package org.example.tasks.utils;

import lombok.AllArgsConstructor;
import org.example.tasks.tasks.data.Task;
import org.example.tasks.tasks.data.entity.EmployeeEntity;
import org.example.tasks.tasks.data.entity.TaskEntity;

@AllArgsConstructor
public class TaskMapperFromTaskEntity {

    public static Task toTask(TaskEntity taskEntity) {
        return new Task(
            taskEntity.getTaskId(),
            taskEntity.getTitle(),
            taskEntity.getDescription(),
            taskEntity.getCreated(),
            taskEntity.getAuthorId() != null ? taskEntity.getAuthorId().getId() : null,
            taskEntity.getExecutorId() != null ? taskEntity.getExecutorId().getId() : null,
            taskEntity.getStatus()
        );
    }

    public static TaskEntity toTaskEntity(Task task, EmployeeEntity author) {
        return new TaskEntity(
            task.getTaskId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreated(),
            author,
            null,
            task.getStatus()
        );
    }
}

