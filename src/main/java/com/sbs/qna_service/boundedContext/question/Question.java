package com.sbs.qna_service.boundedContext.question;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity //SpringBootが該当クラスをENTITYに見る。
public class Question {
    @Id //PKEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT
    private Integer id; //質問データの固有番号

    @Column(length = 200) //VARCHAR(200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content; //内容

    private LocalDateTime createDate;
}