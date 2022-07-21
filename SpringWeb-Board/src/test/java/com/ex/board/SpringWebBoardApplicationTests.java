package com.ex.board;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex.board.entity.comment.Comment;
import com.ex.board.entity.message.Message;
import com.ex.board.repository.MessageRepository;
import com.ex.board.repository.comment.CommentRepository;

@SpringBootTest
class SpringWebBoardApplicationTests {

	
	 @Autowired
	    private MessageRepository messageRepository;

	    @Autowired
	    private CommentRepository commentRepository;

	    @Test
	    void testJpa() {
	        Optional<Message> oq = this.messageRepository.findById(2);
	        assertTrue(oq.isPresent());
	        Message q = oq.get();

	        Comment a = new Comment();
	        a.setContent("네 자동으로 생성됩니다.");
	        a.setMessage(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
	        a.setCreateDate(LocalDateTime.now());
	        this.commentRepository.save(a);
	}
}