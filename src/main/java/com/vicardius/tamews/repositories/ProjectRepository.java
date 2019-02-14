package com.vicardius.tamews.repositories;

import com.vicardius.tamews.models.Project;
import com.vicardius.tamews.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectId(Long projectId);

    Set<Project> findByUsers(User user);

}