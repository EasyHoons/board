package com.ex.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex.board.service.MessageService;

@SpringBootTest
class SpringWebBoardApplicationTests {

	
	 @Autowired
	    private MessageService messageService;

	 

	    @Test
	    void testJpa() {
	        for (int i = 1; i <= 50; i++) {
	            String subject = String.format("테스트 데이터입니다:[%03d]", i);
	            String content = "페이징 테스트 데이터입니다.";
	            this.messageService.create(subject, content,null);
}
	    }
}
