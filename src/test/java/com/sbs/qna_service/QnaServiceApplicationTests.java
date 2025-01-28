package com.sbs.qna_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
		this.questionRepository.save(q1); // 質問と質問内容を保存

		Question q2 = new Question();
		q2.setSubject("SpringBootの質問です。");
		q2.setContent("IDは自動に生成されますか？");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	/*
	 * SQL SELECT * FROM question;
	 */
	@Test
	@DisplayName("findAll")
	void t002() {
		List<Question> all = questionRepository.findAll();
		// select * from table
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("質問１", q.getSubject());
	}

	/*
	 * SQL SELECT * FROM question WHERE id=1;
	 */
	@Test
	@DisplayName("findById")
	void t003() {
		Optional<Question> oq = questionRepository.findById(2);
		if (oq.isPresent()) { // 値の存在を確認 有り：TRUE 無し：FALSE
			Question q = oq.get();
			assertEquals("SpringBootの質問です。", q.getSubject());
		}
	}

	/*
	 * SQL SELECT * FROM question WHERE subject='質問１';
	 */
	@Test
	@DisplayName("findBySubject")
	void t004() {
		Question q = this.questionRepository.findBySubject("質問１");
		assertEquals(1, q.getId());
	}

	/*
	 * SQL SELECT * FROM question WHERE subject='質問１' and
	 * content='質問１の内容内容内容内容内容内容内容内容内容';
	 */
	@Test
	@DisplayName("findBySubjectAndContent")
	void t005() {
		Question q = this.questionRepository.findBySubjectAndContent("質問１", "質問１の内容内容内容内容内容内容内容内容内容");
		assertEquals(1, q.getId());
	}

	/*
	 * SQL SELECT * FROM question WHERE subject LIKE '質問１';
	 */
	@Test
	@DisplayName("findBySubjectLike")
	void t006() {
		List<Question> qList = this.questionRepository.findBySubjectLike("質問%");
		Question q = qList.get(0);
		assertEquals("質問１", q.getSubject());
	}

	/*
	 UPDATE question
	 SET content = ?,
	 create_date = ?,
	 subject = ?,
	 where id = ?,
	 */
	@Test
	@DisplayName("データ修正する")
	void t007() {
		// SQL SELECT * FROM question WHERE id=1;
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("修正されたタイトル");
		questionRepository.save(q);
	}
	
	/*
	 DELETE FROM question where id = ?,
	 */
	@Test
	@DisplayName("データ削除する")
	void t008() {
		// SQL SELECT COUNT(*) FROM question;
		assertEquals(2, questionRepository.count());
		// SQL SELECT * FROM question WHERE id=1;
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		questionRepository.delete(q);
		assertEquals(1, questionRepository.count());
	}


}
