package com.vicardius.tamews.repositories;

import com.vicardius.tamews.models.Comment;
import com.vicardius.tamews.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    Iterable<Comment> findByTask(Task task);

}