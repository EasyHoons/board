package com.ex.board.controller.message;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    		 @RequestParam(value = "kw", defaultValue = "") String kw, 
    		 @RequestParam(value = "Type", defaultValue="") String Type){
		 
		 Page<Message> paging = this.messageService.getList(page,kw,Type);
	        model.addAttribute("paging", paging);
	        model.addAttribute("kw", kw);
	        model.addAttribute("Type",Type);
	        return "message_list";
        
    }
	 
	@RequestMapping(value = "detail/{id}")
	@Transactional
	public String detail(Model model, @PathVariable Integer id, CommentForm commentForm) {
		Message message = this.messageService.getMessage(id);
		
		//디테일페이지를 호출할때 Hit Count ++
		message.setHit(message.getHit()+1);
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
	
	//수정. 수정버튼 누르면 요청. message_Form에서 action속성없이 전달되는 URL(URL 그대로 요청)을 받기위해 id값도 추가.
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String messageModify(MessageForm messageForm, @PathVariable("id") Integer id, Principal principal) {
        Message message = this.messageService.getMessage(id);
        //현재 로그인된유저(principal)와 message객체의 유저가 다르면 출력
//        if(!message.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
        messageForm.setSubject(message.getSubject());
        messageForm.setContent(message.getContent());
        return "message_form";
    }
	
	//수정. 수정폼에서 저장하기 누르면 요청.
	@PreAuthorize("isAuthenticated()")
    @PostMapping("modify/{id}")
    public String messageModify(@Valid MessageForm messageForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "message_form";
        }
        
        Message message = this.messageService.getMessage(id);
//        if (!message.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
        this.messageService.modify(message, messageForm.getSubject(), messageForm.getContent());
        return String.format("redirect:/message/detail/%s", id);
    }

	
	//삭제. id로 부른 오브젝트를 삭제.
	@PreAuthorize("isAuthenticated()")
    @GetMapping("delete/{id}")
    public String messageDelete(Principal principal, @PathVariable("id") Integer id) {
		Message message = this.messageService.getMessage(id);
//        if (!message.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//        }
        this.messageService.delete(message);
        return "redirect:/";
    }
	 
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Message message = this.messageService.getMessage(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.messageService.vote(message, siteUser);
        return String.format("redirect:/message/detail/%s", id);
    }
	
}