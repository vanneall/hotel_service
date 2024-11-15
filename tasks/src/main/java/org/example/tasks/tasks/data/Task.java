package org.example.tasks.tasks.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    Long taskId;
    String title;
    String description;
    String created;
    String authorId;
    String executorId;
    Status status;
}
