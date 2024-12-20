package org.example.tasks.tasks.data.repository;


import org.example.tasks.tasks.data.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findAll(Pageable pageable);
}
