package org.example.repository;

import org.example.entities.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Long> {
    List<WorkSpace> findByAvailableTrue();
}
