package org.example.hotel_service.utils.mapper;

import org.example.hotel_service.tasks.data.Status;
import org.example.hotel_service.tasks.data.Task;
import org.example.hotel_service.tasks.data.TaskRequest;

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
