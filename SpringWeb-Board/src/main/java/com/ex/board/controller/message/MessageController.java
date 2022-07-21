package com.ex.board.controller.message;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.board.entity.message.Message;
import com.ex.board.service.MessageService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class MessageController {

	 private final  MessageService messageService;
	

	 @RequestMapping("/message/list")
     public String messageList(Model model) {
		 
		List<Message> messageList = messageService.getList(); 
		model.addAttribute("messageList", messageList);
        return "message_list";
        
    }
	@RequestMapping(value = "/message/detail/{id}")
	public String detail(Model model, @PathVariable Integer id) {
		Message message = this.messageService.getMessage(id);
		model.addAttribute("message", message);
		return "message_detail";
	}
	
	
	 
}