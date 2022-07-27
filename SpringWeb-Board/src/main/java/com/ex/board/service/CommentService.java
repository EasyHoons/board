package com.ex.board.service;

import java.time.LocalDateTime;
import java.util.Optional;

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

	public void create(Message message, String content, SiteUser author) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setCreateDate(LocalDateTime.now());
		comment.setMessage(message);
		comment.setAuthor(author);

		this.commentRepository.save(comment);
	}

	// 답변조회
	public Comment getComment(Integer id) {
		Optional<Comment> comment = this.commentRepository.findById(id);
		if (comment.isPresent()) {
			return comment.get();
		} else {
			throw new DataNotFoundException("comment not found");
		}
	}

	// 답변수정
	public void modify(Comment comment, String content) {
		comment.setContent(content);
		comment.setModifyDate(LocalDateTime.now());
		this.commentRepository.save(comment);
	}
	
	// 답변삭제
	public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }
}