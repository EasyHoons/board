package com.ex.board.service.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyForm {

	private String username;
	
	private String password1;
	
	private String password2;
	
	@Email
	private String email;
	
}
