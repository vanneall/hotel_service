package org.example.tasks.tasks.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tasks.tasks.data.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long taskId;
    private String title;
    private String description;
    private String created;

    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private EmployeeEntity authorId;
    @OneToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private EmployeeEntity executorId;
    @Enumerated(EnumType.STRING)
    private Status status;
}
