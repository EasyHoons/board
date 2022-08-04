package com.ex.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ex.board.entity.Message;
import com.ex.board.entity.SiteUser;

public interface MessageRepository extends JpaRepository<Message, Integer>{
	 
	Page<Message> findAll(Pageable pageable);
	 Page<Message> findAll(Specification<Message> spec, Pageable pageable);
	 
	 //for count a user's messages
	 long countByAuthor(SiteUser author);
	 @Query(value= "select count(*) from message where author_id=?1", nativeQuery=true)
	 Long count(int i);//for test @Query+SQL.
	 
	 @Query(value = "select m from Message m")
	 List<Message> selectAllJPQL();//for test @Query+JPQL
	 
	 @Query(value =  "select * from message", nativeQuery = true)
	 List<Message> justSubject();//for test @Quesry+SQL
	 
//	 @Transactional
//	 @Modifying
//	 @Query(value= "update Message set subject=#{#paramMessage.subject}, content=#{#paramMessage.content} where id = ?1 ", nativeQuery=true)
//	 List<Message> updatethis(@Param(value="paramMessage")Message message, int id);// object parameter = #{#paramMesaage...}. error??
		 
}
