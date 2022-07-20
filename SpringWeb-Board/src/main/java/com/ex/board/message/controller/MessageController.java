package com.ex.board.message.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.board.entity.message.Message;
import com.ex.board.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {

	 private final  MessageRepository messageRepository;
	

	 @RequestMapping("/message/list")
     public String messageList(Model model) {
	  List<Message> messageList = this.messageRepository.findAll();
     model.addAttribute("messageList", messageList);
        return "message_list";
        
    }
	
}
