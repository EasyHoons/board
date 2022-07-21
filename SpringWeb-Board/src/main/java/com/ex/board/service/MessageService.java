package com.ex.board.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ex.board.entity.message.Message;
import com.ex.board.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {
	private final MessageRepository messageRepository;
	
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
}
