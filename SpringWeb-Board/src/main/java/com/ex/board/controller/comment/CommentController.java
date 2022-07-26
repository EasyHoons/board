package com.ex.board.controller.comment;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	    @PostMapping("create/{id}")
	    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commmentForm, BindingResult bindingResult) {
	        Message message = this.messageService.getMessage(id);
	        if(bindingResult.hasErrors()) {
	        	model.addAttribute("message",message);
	        	return "message_detail";
	        }
	        	
	        this.commentService.create(message, commmentForm.getContent());

	        return String.format("redirect:/message/detail/%s", id);

          }
}