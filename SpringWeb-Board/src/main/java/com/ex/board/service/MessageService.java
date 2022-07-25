package com.ex.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
	
	public void create(String subject, String content) {
		Message ms = new Message();
		ms.setSubject(subject);
		ms.setContent(content);
		ms.setCreateDate(LocalDateTime.now());
		messageRepository.save(ms);
	}
	 public Page<Message> getList(int page) {
		 	List<Sort.Order> sorts = new ArrayList<>();
		 	sorts.add(Sort.Order.desc("createDate"));
//		 	page = (this.pageable.getPageNumber() == 0) ? 0 : (this.pageable.getPageNumber() - 1);
		 	
	        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
	        return this.messageRepository.findAll(pageable);
	    }

	 public Long count() {
	        return messageRepository.count();
	    }
}
