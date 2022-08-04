package com.ex.board.Security;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyForm {

	@NotEmpty(message = "Password를 입력하십시오.")
	private String password1;//확인패스워드입력
	@NotEmpty(message = "Password를 입력하십시오.")
	private String password2;//확인패스워드입력

}
