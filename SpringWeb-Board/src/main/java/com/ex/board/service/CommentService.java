package com.ex.board.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ex.board.entity.Comment;
import com.ex.board.entity.Message;
import com.ex.board.entity.SiteUser;
import com.ex.board.repository.CommentRepository;
import com.ex.board.service.form.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	public Comment create(Message message, String content, SiteUser author) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setCreateDate(LocalDateTime.now());
		comment.setMessage(message);
		comment.setAuthor(author);
		this.commentRepository.save(comment);
		return comment;

		
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
	
	public void vote(Comment comment, SiteUser siteUser) {
        comment.getVoter().add(siteUser);
        this.commentRepository.save(comment);
    }
	
	public Long usersTotalComment(SiteUser user) {
		long totalcomment = this.commentRepository.countByAuthor(user);
		return totalcomment; 
	}
}