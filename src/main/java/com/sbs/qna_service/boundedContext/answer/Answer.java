package com.sbs.qna_service.boundedContext.answer;

import java.time.LocalDateTime;

import com.sbs.qna_service.boundedContext.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity // SpringBootが該当クラスをENTITYに見る。
public class Answer {
	@Id // PKEY
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private Integer id; // 質問データの固有番号

	@Column(columnDefinition = "TEXT")
	private String content; // 内容

	private LocalDateTime createDate;

	@ManyToOne
	private Question question;
}