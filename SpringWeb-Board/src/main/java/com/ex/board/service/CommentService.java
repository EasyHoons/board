package com.ex.board.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ex.board.Security.SiteUser;
import com.ex.board.entity.comment.Comment;
import com.ex.board.entity.message.Message;
import com.ex.board.repository.comment.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	
	 private final CommentRepository commentRepository;
	
	 
	 public void create(Message message, String content,SiteUser author) {
	        Comment comment = new Comment();
	        comment.setContent(content);
	        comment.setCreateDate(LocalDateTime.now());
	        comment.setMessage(message);
	        comment.setAuthor(author);
	        
	        this.commentRepository.save(comment);
 }
}