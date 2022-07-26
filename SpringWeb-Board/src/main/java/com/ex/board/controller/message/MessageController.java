package com.ex.board.controller.message;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.board.Security.SiteUser;
import com.ex.board.Security.UserService;
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
	 private final UserService userService;
	

	 @RequestMapping("list")
     public String messageList(Model model, @RequestParam(value="page", defaultValue="0") int page,
    		 @RequestParam(value = "kw", defaultValue = "") String kw) {
		 
		 Page<Message> paging = this.messageService.getList(page,kw);
	        model.addAttribute("paging", paging);
	        model.addAttribute("kw", kw);
        return "message_list";
        
    }
	@RequestMapping(value = "detail/{id}")
	public String detail(Model model, @PathVariable Integer id, CommentForm commentForm) {
		Message message = this.messageService.getMessage(id);
		model.addAttribute("message", message);
		return "message_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("create")
	public String messageCreate(MessageForm messageForm) {
		
		return "message_Form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("create")
	public String messageCreate(@Valid MessageForm messageFrom, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "message_form";
		}
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		messageService.create(messageFrom.getSubject(), messageFrom.getContent(),siteUser);
		return "redirect:/message/list";
	}

	 
}