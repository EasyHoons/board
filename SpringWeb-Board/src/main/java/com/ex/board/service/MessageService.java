package com.ex.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ex.board.Security.SiteUser;
import com.ex.board.entity.comment.Comment;
import com.ex.board.entity.message.Message;
import com.ex.board.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {
	private final MessageRepository messageRepository;
//	private final Pageable pageable;
	
	public List<Message> getList(){
		
		return messageRepository.findAll();
	}
	
	public Message getMessage(Integer id) {
		Optional<Message> message = this.messageRepository.findById(id);
		
		return message.get();
		}
	
	public void create(String subject, String content, SiteUser user) {
		Message ms = new Message();
		ms.setSubject(subject);
		ms.setContent(content);
		ms.setCreateDate(LocalDateTime.now());
		ms.setAuthor(user);
		messageRepository.save(ms);
	}
	private Specification<Message> serch(String kw, String Type) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;
			
			public Predicate toPredicate(Root<Message> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);  // 중복을 제거 
				Join<Message, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Message, Comment> a = q.join("commentList", JoinType.LEFT);
				Join<Comment, SiteUser> u2 = a.join("author", JoinType.LEFT);
				
				if(Type.equalsIgnoreCase("3")) {
					return	cb.or(cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
						cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
				}
				if(Type.equalsIgnoreCase("comment")) {
					return cb.or(cb.like(a.get("content"), "%" + kw + "%"));      // 답변 내용 
				}
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
						cb.like(q.get("content"), "%" + kw + "%"));  // 내용  
			}
			
		};
		
	}
	
	 public Page<Message> getList(int page, String kw, String Type) {
		 	List<Sort.Order> sorts = new ArrayList<>();
		 	sorts.add(Sort.Order.desc("createDate"));
		 	
	        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
	        Specification<Message> spec = serch(kw,Type);
	        return this.messageRepository.findAll(spec, pageable);
	    }

	 public Long count() {
	        return messageRepository.count();
	    }
	 
	 //수정
	 public void modify(Message message, String subject, String content) {
		 message.setSubject(subject);
		 message.setContent(content);
		 message.setModifyDate(LocalDateTime.now());
	        this.messageRepository.save(message);
	    }
	 
	 //삭제
	 public void delete(Message message) {
	        this.messageRepository.delete(message);
	    }
	 //추천 
	 public void vote(Message message, SiteUser siteUser) {
	        message.getVoter().add(siteUser);
	        this.messageRepository.save(message);
	    }

}
