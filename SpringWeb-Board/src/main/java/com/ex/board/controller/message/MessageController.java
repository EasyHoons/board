package com.ex.board.controller.message;

import java.util.List;

<<<<<<< HEAD
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.board.entity.message.Message;
import com.ex.board.service.CommentForm;
import com.ex.board.service.MessageForm;
import com.ex.board.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/message/")
public class MessageController {

	 private final  MessageService messageService;
	

	 @RequestMapping("list")
     public String messageList(Model model) {
		 
		List<Message> messageList = messageService.getList(); 
		model.addAttribute("messageList", messageList);
        return "message_list";
        
    }
	@RequestMapping(value = "detail/{id}")
	public String detail(Model model, @PathVariable Integer id, CommentForm commentForm) {
		Message message = this.messageService.getMessage(id);
		model.addAttribute("message", message);
		return "message_detail";
	}
	
	
	@GetMapping("create")
	public String messageCreate(MessageForm messageForm) {
		
		return "message_Form";
	}
	
	@PostMapping("create")
	public String messageCreate(@Valid MessageForm messageFrom, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "message_form";
		}
		messageService.create(messageFrom.getSubject(), messageFrom.getContent());
		return "redirect:/message/list";
	}
=======
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
	
	
>>>>>>> refs/remotes/origin/KIM
	 
}