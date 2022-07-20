package com.ex.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String root(){
		return "redirect:/index";
	}
	
	@ResponseBody
	@RequestMapping("/index")
	public String index() {
		return "hghi";
	}
	

}
