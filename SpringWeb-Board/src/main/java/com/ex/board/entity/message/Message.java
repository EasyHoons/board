package com.ex.board.entity.message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ex.board.Security.SiteUser;
import com.ex.board.entity.comment.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;
    
    @OneToMany(mappedBy = "message", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
    
    @ManyToOne
    private SiteUser author;
    
    private LocalDateTime createDate;
    
    private LocalDateTime modifyDate;
    
    @ManyToMany
    Set<SiteUser> voter;
    
    
    @Column(columnDefinition = "int default 0", nullable = false)
    private int hit;
}
