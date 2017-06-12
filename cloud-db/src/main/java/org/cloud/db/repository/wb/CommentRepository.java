package org.cloud.db.repository.wb;

import org.cloud.db.entity.wb.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	
}