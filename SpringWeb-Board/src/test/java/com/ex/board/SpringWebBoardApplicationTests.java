package com.ex.board;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex.board.entity.message.Message;
import com.ex.board.repository.MessageRepository;

@SpringBootTest
class SpringWebBoardApplicationTests {

	
	@Autowired
    private MessageRepository messagerepository;
	
	@Test
	void contextLoads() {
		
		Message m1 = new Message();
        m1.setSubject("sbb가 무엇인가요?");
        m1.setContent("sbb에 대해서 알고 싶습니다.");
        m1.setCreateDate(LocalDateTime.now());
        this.messagerepository.save(m1);  // 첫번째 질문 저장

        Message m2 = new Message();
        m2.setSubject("스프링부트 모델 질문입니다.");
        m2.setContent("id는 자동으로 생성되나요?");
        m2.setCreateDate(LocalDateTime.now());
        this.messagerepository.save(m2);  // 두번째 질문 저장
	}

}
