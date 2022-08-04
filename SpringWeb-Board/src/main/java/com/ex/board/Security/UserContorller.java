package com.ex.board.Security;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ex.board.service.CommentService;
import com.ex.board.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserContorller {
	
	private final UserService userService;
	private final MessageService messageService;
	private final CommentService commentService;
		
	@GetMapping("/signup")
	public String CreateUser(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String CreateUser(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordIncorrect","2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		
		try {
			this.userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		}catch(Exception e) {
			e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
		}
		
		return "redirect:/";
	}

	@RequestMapping("/mypage")
	public String ModifyUser(Model model, UserModifyForm usermodifyform,  Principal  principal) {

		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		model.addAttribute("siteUser", siteUser);		
		model.addAttribute("usermodifyform",usermodifyform);
		model.addAttribute("totalcomment",this.commentService.usersTotalComment(siteUser));
		model.addAttribute("totalmessage",this.messageService.usersTotalMessage(siteUser));
		
		return "mypage";
	}
	
	//비밀번호 변경. 작업 수행후 리다이렉트 mypage
	@PostMapping("/modify")
	public String ModifyUser(@Valid UserModifyForm usermodifyform, BindingResult bindingResult,Principal principal,RedirectAttributes redirect) {
		
		String result="1";
		
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(usermodifyform.getPassword1(), this.userService.getUser(principal.getName()).getPassword())){
			SiteUser siteUser = this.userService.getUser(principal.getName());
			this.userService.modify(siteUser.getId(), usermodifyform);
			result="2";
			redirect.addAttribute("result",result);
			
			return "redirect:/user/mypage";
	
		}
		return "redirect:/user/mypage";	
	}

	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
	
	
}
