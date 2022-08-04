package com.ex.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex.board.Security.SiteUser;
import com.ex.board.Security.UserRepository;
import com.ex.board.entity.message.Message;
import com.ex.board.repository.MessageRepository;
import com.ex.board.service.MessageService;

@SpringBootTest
class SpringWebBoardApplicationTests {

	
	 @Autowired
	    private MessageService messageService;
	 @Autowired
	 	private MessageRepository messagerepository;
	 @Autowired
	 	private UserRepository userrepository;

	 

	    @Test
	    void testJpa() {
	    	
	    	
//	        for (int i = 1; i <= 50; i++) {
//	            String subject = String.format("테스트 데이터입니다:[%03d]", i);
//	            String content = "페이징 테스트 데이터입니다.";
//	            this.messageService.create(subject, content,null);
//				}
	    	
	    	//test JPA
//	    	List<Message> all = this.messagerepository.findAll();
//	    	SiteUser siteuser=this.userrepository.getById((long) 4); //id로 유저 호출
//	    	System.out.println(all.size());    	
//	    	System.out.println(this.messagerepository.count());
//	    	long test = this.messagerepository.countByAuthor(siteuser); //호출한 유저가 쓴 글수
//	    	System.out.println(test);
	    	
	    	
//	    	SiteUser siteuser=this.userrepository.getById((long) 4);
//	    	System.out.println(this.messagerepository.countAuthor(siteuser));
	    	List<Message> all = this.messagerepository.selectAllJPQL();
	    	System.out.println(all.get(1));
	    	System.out.println(this.messagerepository.justSubject().get(1).toString());
	    	System.out.println(this.messagerepository.count(4));

	    }
}
