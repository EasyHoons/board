package com.ex.board.controller;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.ex.board.entity.Comment;
import com.ex.board.entity.Message;
import com.ex.board.entity.SiteUser;
import com.ex.board.service.CommentService;
import com.ex.board.service.MessageService;
import com.ex.board.service.UserService;
import com.ex.board.service.form.CommentForm;

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
	    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commmentForm, BindingResult bindingResult,Principal principal, Comment commentForm) {
	        Message message = this.messageService.getMessage(id);
	        SiteUser siteUser = this.userService.getUser(principal.getName());
	        if(bindingResult.hasErrors()) {
	        	model.addAttribute("message",message);
	        	return "message_detail";
	        }
	        	
	       Comment comment = this.commentService.create(message,
	               commentForm.getContent(), siteUser);
	        return String.format("redirect:/message/detail/%s#comment_%s",
	                comment.getMessage().getId(), comment.getId());

          }
	 	
	 	//수정. 수정버튼 누르면 get요청
	 	@PreAuthorize("isAuthenticated()")
	    @GetMapping("modify/{id}")
	    public String commentModify(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
	 		Comment comment = this.commentService.getComment(id);
//	        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
//	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//	        }
	        commentForm.setContent(comment.getContent());
	        return "comment_form";
	    }
	 	
	 	//수정. 확인버튼 누르면 post요청
	 	@PreAuthorize("isAuthenticated()")
	    @PostMapping("modify/{id}")
	    public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
	            @PathVariable("id") Integer id, Principal principal) {
	        if (bindingResult.hasErrors()) {
	            return "comment_form";
	        }
	        Comment comment = this.commentService.getComment(id);
	        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	        this.commentService.modify(comment, commentForm.getContent());
	        return String.format("redirect:/message/detail/%s#comment_%s", 
	                comment.getMessage().getId(), comment.getId());
	    }
	 	
	 	//삭제
	 	@PreAuthorize("isAuthenticated()")
	    @GetMapping("/delete/{id}")
	    public String CommentDelete(Principal principal, @PathVariable("id") Integer id) {
	        Comment Comment = this.commentService.getComment(id);
	        if (!Comment.getAuthor().getUsername().equals(principal.getName())) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
	        }
	        this.commentService.delete(Comment);
	        return String.format("redirect:/message/detail/%s", Comment.getMessage().getId());
	    }
	 	
	 	@PreAuthorize("isAuthenticated()")
	    @GetMapping("/vote/{id}")
	    public String commentVote(Principal principal, @PathVariable("id") Integer id) {
	        Comment comment = this.commentService.getComment(id);
	        SiteUser siteUser = this.userService.getUser(principal.getName());
	        this.commentService.vote(comment, siteUser);
	        return String.format("redirect:/message/detail/%s#comment_%s", 
	                comment.getMessage().getId(), comment.getId());
	    }
	 	
	 	
	 	
}