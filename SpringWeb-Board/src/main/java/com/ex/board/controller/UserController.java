package com.ex.board.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ex.board.entity.SiteUser;
import com.ex.board.service.CommentService;
import com.ex.board.service.MessageService;
import com.ex.board.service.UserService;
import com.ex.board.service.form.PwCompareForm;
import com.ex.board.service.form.UserCreateForm;
import com.ex.board.service.form.UserModifyForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final MessageService messageService;
	private final CommentService commentService;

	@GetMapping("/login")
	public String login() {
		
		return "login_form";
	}
		
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
	public String ModifyUser(Model model, PwCompareForm pwcompareform,  Principal  principal) {

		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		model.addAttribute("siteUser", siteUser);		
		model.addAttribute("pwcompareform",pwcompareform);
		model.addAttribute("totalcomment",this.commentService.usersTotalComment(siteUser));
		model.addAttribute("totalmessage",this.messageService.usersTotalMessage(siteUser));
		
		return "mypage";
	}
	
	//비밀번호 체크. 작업 수행후 리다이렉트 mypage
	@PostMapping("/modify")
	public String ModifyUser(@Valid PwCompareForm pwcompareform, BindingResult bindingResult,Principal principal,RedirectAttributes redirect) {
		
		String result="1";
		
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(pwcompareform.getPassword1(), this.userService.getUser(principal.getName()).getPassword())){//입력받은 비밀번호가 현재 유저의 비밀번호와 같음.
			return "redirect:/user/infomodify";
		}
		else {
		result="2";
		redirect.addAttribute("result",result);
		return "redirect:/user/mypage";
		}	
	}
	
	@GetMapping("/infomodify")
	public String infoModify(UserModifyForm userModifyForm, Principal principal, Model model){
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		model.addAttribute("userModifyForm", userModifyForm);
		model.addAttribute("siteUser", siteUser);
		
		return "info_modify";
	}
	
	@PostMapping("/infomodify")
	public String infoModify(@Valid UserModifyForm userModifyForm, BindingResult bindingResult, Principal principal, Model model ) {
		
		//todo : 회원가입폼과 비슷하게 만들기
		SiteUser siteUser = this.userService.getUser(principal.getName());
		long id = siteUser.getId();
		model.addAttribute("siteUser", siteUser);

		if(bindingResult.hasErrors()) {
			return "info_modify";
		}
		
		if(!userModifyForm.getPassword1().equals(userModifyForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordIncorrect","2개의 패스워드가 일치하지 않습니다.");
			return "info_modify";
		}
		try {
			this.userService.modify(id, userModifyForm.getUsername(), userModifyForm.getEmail(),
					userModifyForm.getPassword1());
		} catch (DataIntegrityViolationException e) {			
			bindingResult.reject("email duplicated","중복된 이메일입니다.");
			return "info_modify";
		} 
		return "redirect:/";
	}
	
}
