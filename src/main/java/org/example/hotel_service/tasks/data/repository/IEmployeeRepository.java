package org.example.hotel_service.tasks.data.repository;

import org.example.hotel_service.tasks.data.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, String> {
}
