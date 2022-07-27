package com.ex.board.entity.comment;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.ex.board.Security.SiteUser;
import com.ex.board.entity.message.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Message message;
    
    @ManyToOne
    private SiteUser author;
    
    private LocalDateTime createDate;
    
    private LocalDateTime modifyDate;
    
    @ManyToMany
    Set<SiteUser> voter;

}
