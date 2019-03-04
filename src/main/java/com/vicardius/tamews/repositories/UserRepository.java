package com.vicardius.tamews.repositories;

import com.vicardius.tamews.models.Project;
import com.vicardius.tamews.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByIdUser(Long Id);

    Iterable<User> findByProjects(Project project);

}