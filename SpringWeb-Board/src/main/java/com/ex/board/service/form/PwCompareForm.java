package com.ex.board.service.form;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwCompareForm {

	@NotEmpty(message = "Password를 입력하십시오.")
	private String password1;//확인패스워드입력

}
