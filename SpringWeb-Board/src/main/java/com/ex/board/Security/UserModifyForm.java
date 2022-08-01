package com.ex.board.Security;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyForm {


	@NotEmpty(message = "Password를 입력하십시오.")
	private String password2;//확인패스워드입력

}
