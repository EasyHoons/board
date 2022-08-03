package com.ex.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.board.Security.UserService;
import com.ex.board.repository.MessageRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class HomeController {
	private final MessageRepository messageRepository;
	private final UserService userService;
	
	@RequestMapping("/")
	    public String root() {
	        return "redirect:/message/list";
	}
	
//	@PreAuthorize("isAuthenticated()")
//	@RequestMapping("/mypage")
//		public String mypage(Model model, Principal  principal) {
//		
//		SiteUser siteUser = this.userService.getUser(principal.getName());
//		model.addAttribute("siteUser", siteUser);
//		
//		return "mypage";
//	}
	

}
