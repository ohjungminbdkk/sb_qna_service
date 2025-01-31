package com.sbs.qna_service.boundedContext.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sbs.qna_service.boundedContext.DataNotFoundException;
import com.sbs.qna_service.boundedContext.answer.Answer;
import com.sbs.qna_service.boundedContext.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepository;

	public List<Question> findAll() {
		return questionRepository.findAll();
	}

	public Question getQuestion(Integer id) {
		Optional<Question> question = questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public Question create(String subject, String content, SiteUser author) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setAuthor(author);
		question.setCreateDate(LocalDateTime.now());
		questionRepository.save(question);
		
		return question;
	}

	public Page<Question> getList(int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));// 작성일자 순으로 정렬
		// page : 요청된 페이지 번호(0부터 시작)
		// 10 : 한 페이지에 표시할 데이터 갯수
		// Sort.by(sorts) : 전렬 기준, 'createDate'를 기준을 적용
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한페이지에 10개씩
		return questionRepository.findAll(pageable);
	}
}
