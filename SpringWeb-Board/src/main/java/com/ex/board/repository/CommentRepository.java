package com.ex.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.board.entity.Comment;
import com.ex.board.entity.SiteUser;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	long countByAuthor(SiteUser author);
}
