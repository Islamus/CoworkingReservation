package org.example.services;

import org.example.entities.WorkSpace;
import org.example.repository.WorkSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkSpaceService {

    private final WorkSpaceRepository workspaceRepository;

    public WorkSpaceService(WorkSpaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public List<WorkSpace> findAll() {
        return workspaceRepository.findAll();
    }

    public void save(WorkSpace workspace) {
        workspaceRepository.save(workspace);
    }
}
