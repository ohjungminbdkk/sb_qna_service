package com.sbs.qna_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.sbs.qna_service.boundedContext.answer.Answer;
import com.sbs.qna_service.boundedContext.answer.AnswerRepository;
import com.sbs.qna_service.boundedContext.question.Question;
import com.sbs.qna_service.boundedContext.question.QuestionRepository;
import com.sbs.qna_service.boundedContext.question.QuestionService;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.transaction.Transactional;

@SpringBootTest
class QnaServiceApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Autowired // フィールド注入
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@BeforeEach // テストケースが実行する前に一回実行する
	void beforeEach() {
		// すべてのデータ削除
		questionRepository.deleteAll();
		// 痕跡削除 （次のINSERT際にIDが1に設定させるために）
		questionRepository.clearAutoIncrement();

		// すべてのデータ削除
		answerRepository.deleteAll();
		// 痕跡削除 （次のINSERT際にIDが1に設定させるために）
		answerRepository.clearAutoIncrement();

		// 質問生成
		Question q1 = new Question();
		q1.setSubject("質問１");
		q1.setContent("質問１の内容内容内容内容内容内容内容内容内容");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1); // 質問と質問内容を保存

		Question q2 = new Question();
		q2.setSubject("SpringBootの質問です。");
		q2.setContent("IDは自動に生成されますか？");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);

		// コメント生成
		Answer a1 = new Answer();
		a1.setContent("生成されて保管されます。");
		a1.setQuestion(q2);
		q2.addAnswer(a1);
//		q2.getAnswerList().add(a1);

		a1.setCreateDate(LocalDateTime.now());
		answerRepository.save(a1);
	}

	@Test
	// @DisplayName : テストの意図を人が読みやすい形で説明
	@DisplayName("データを保存する")
	void t001() {
		Question q = new Question();
		q.setSubject("冬は寒いですか");
		q.setContent("はい。さむいです。");
		q.setCreateDate(LocalDateTime.now());
		questionRepository.save(q); // 質問と質問内容を保存
		assertEquals("冬は寒いですか", questionRepository.findById(3).get().getSubject());
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
		Question q = questionRepository.findBySubject("質問１");
		assertEquals(1, q.getId());
	}

	/*
	 * SQL SELECT * FROM question WHERE subject='質問１' and
	 * content='質問１の内容内容内容内容内容内容内容内容内容';
	 */
	@Test
	@DisplayName("findBySubjectAndContent")
	void t005() {
		Question q = questionRepository.findBySubjectAndContent("質問１", "質問１の内容内容内容内容内容内容内容内容内容");
		assertEquals(1, q.getId());
	}

	/*
	 * SQL SELECT * FROM question WHERE subject LIKE '質問１';
	 */
	@Test
	@DisplayName("findBySubjectLike")
	void t006() {
		List<Question> qList = questionRepository.findBySubjectLike("質問%");
		Question q = qList.get(0);
		assertEquals("質問１", q.getSubject());
	}

	/*
	 * UPDATE question SET content = ?, create_date = ?, subject = ?, where id = ?,
	 */
	@Test
	@DisplayName("データ修正する")
	void t007() {
		// SQL SELECT * FROM question WHERE id=1;
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("修正されたタイトル");
		questionRepository.save(q);
	}

	/*
	 * DELETE FROM question where id = ?
	 */
	@Test
	@DisplayName("データ削除する")
	void t008() {
		// SQL SELECT COUNT(*) FROM question;
		assertEquals(2, questionRepository.count());
		// SQL SELECT * FROM question WHERE id=1;
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		questionRepository.delete(q);
		assertEquals(1, questionRepository.count());
	}

	/*
	 * 特定の質問を取得する SELECTE FROM question WHERE id = ?
	 * 
	 * 質問に対するコメント保管 INSERT INTO answer SET create_date = NOW(), content = ?,
	 * question_id= ? ;
	 * 
	 */
	@Test
	@DisplayName("コメント生成後、保管")
	void t009() {
		Optional<Question> oq = questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		/*
		 * v1 Optional<Question> oq = questionRepository.findById(2);
		 * assertTrue(oq.isPresent()); Question q = oq.get();
		 */

		/*
		 * v2 Question q = questionRepository.findById(2).orElse(null));
		 */

		Answer a = new Answer();
		a.setContent("生成されて保管されます。");
		a.setQuestion(q); // 度の質問に対するコメント化知るために
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}

	/*
	 * SELECT A.*, Q.* FROM answer AS A LEFT JOIN question AS Q on Q.id =
	 * A.question_id WHERE A.id=?;
	 * 
	 **/
	@Test
	@DisplayName("特定のコメントを取得する")
	void t010() {
		Optional<Answer> qa = answerRepository.findById(1);
		assertTrue(qa.isPresent());
		Answer a = qa.get();

		assertEquals(1, a.getId());
	}

	/*
	 * EAGERの場合。以下のJOINを実行される SELECT Q.*, A.* FROM answer AS Q LEFT JOIN answer AS A
	 * on Q.id = A.question_id WHERE Q.id=?;
	 * 
	 **/
	// テストコードにてはTransactionalを使用しなければならない。
	// findByIdメソッドの実行後では、DBが切れてしまうため。
	// Transactionalアノテーションを使うとメソッドが終わりまで、DBが接続されている。
	@Transactional
	@Test
	@DisplayName("質問でコメント取得")
	@Rollback(false) // テストメソッドが終わった後にも、TransactionがRollbackしなくて、Commitされる。
	void t011() {
		// SQL : SELECT * FROM question WHERE id = 2;
		Optional<Question> oq = questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		// テスト環境では、Getして取得後にDB接続が切れてしまう。

		// Transactionalを使う理由は下のSQL実行まで、DBの接続がつないでないため。
		// または、fetchタイプを変更。ただ、お勧めしない。@OneToMany(mappedBy = "question", cascade =
		// CascadeType.REMOVE, fetch = FetchType.EAGER)
		// 下SQL : SELECT * FROM answer WHERE question_id = 2;を実行する。
		List<Answer> answerList = q.getAnswerList();// DB接続が切れたので、answer取得失敗

		assertEquals(1, answerList.size());
		assertEquals("生成されて保管されます。", answerList.get(0).getContent());
	}

	@Test
	@DisplayName("대량의 테스트 데이터 만들기")
	void t012() {
		IntStream.rangeClosed(3, 300)
				.forEach(no -> questionService.create("테스트 제목입니다. %d".formatted(no), "테스트 내용입니다. %d".formatted(no)));
	}

}
