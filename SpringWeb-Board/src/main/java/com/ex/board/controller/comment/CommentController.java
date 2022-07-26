package com.ex.board.controller.comment;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.board.Security.SiteUser;
import com.ex.board.Security.UserService;
import com.ex.board.entity.message.Message;
import com.ex.board.service.CommentForm;
import com.ex.board.service.CommentService;
import com.ex.board.service.MessageService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/comment/")
@RequiredArgsConstructor
@Controller
public class CommentController {
	
	 private final MessageService messageService;
	 private final CommentService commentService;
	 private final UserService userService;

	 	@PreAuthorize("isAuthenticated()")
	    @PostMapping("create/{id}")
	    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commmentForm, BindingResult bindingResult,Principal principal) {
	        Message message = this.messageService.getMessage(id);
	        SiteUser siteUser = this.userService.getUser(principal.getName());
	        if(bindingResult.hasErrors()) {
	        	model.addAttribute("message",message);
	        	return "message_detail";
	        }
	        	
	        this.commentService.create(message, commmentForm.getContent(),siteUser);

	        return String.format("redirect:/message/detail/%s", id);

          }
}