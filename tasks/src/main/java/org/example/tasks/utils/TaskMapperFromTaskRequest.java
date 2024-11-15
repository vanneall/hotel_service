package org.example.tasks.utils;

import org.example.tasks.tasks.data.Status;
import org.example.tasks.tasks.data.Task;
import org.example.tasks.tasks.request.TaskRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TaskMapperFromTaskRequest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Task toEntity(TaskRequest request) {
        return new Task(
            new Random().nextLong(),
            request.getTitle(),
            request.getDescription(),
            LocalDateTime.now().format(formatter),
            request.getAuthorId(),
            null,
            Status.CREATED
        );
    }
}
