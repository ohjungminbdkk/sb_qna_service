package com.sbs.qna_service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbs.qna_service.boundedContext.question.Question;
import com.sbs.qna_service.boundedContext.question.QuestionRepository;

@SpringBootTest
class QnaServiceApplicationTests {

	@Autowired // フィールド注入
	private QuestionRepository questionRepository;

	@Test
	// @DisplayName : テストの意図を人が読みやすい形で説明
	@DisplayName("データを保存する")
	void t001() {
        Question q1 = new Question();
        q1.setSubject("質問１");
        q1.setContent("質問１の内容内容内容内容内容内容内容内容内容");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 質問と質問内容を保存

        Question q2 = new Question();
        q2.setSubject("SpringBootの質問です。");
        q2.setContent("IDは自動に生成されますか？");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2); 
	}

}
