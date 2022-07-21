package com.ex.board.controller.comment;

<<<<<<< HEAD
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.board.entity.message.Message;
import com.ex.board.service.CommentForm;
import com.ex.board.service.MessageService;
import com.ex.board.service.comment.CommentService;

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
=======
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.board.entity.message.Message;
import com.ex.board.service.MessageService;
import com.ex.board.service.comment.CommentService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
	
	 private final MessageService messageService;
	 private final CommentService commentService;

	    @PostMapping("/create/{id}")
	    public String createComment(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
	        Message message = this.messageService.getMessage(id);
	        this.commentService.create(message, content);
>>>>>>> refs/remotes/origin/KIM
	        return String.format("redirect:/message/detail/%s", id);

          }
}