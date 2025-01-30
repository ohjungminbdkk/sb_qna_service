package com.sbs.qna_service.boundedContext.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sbs.qna_service.boundedContext.question.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	 public void create(Question question, String content) {
		 Answer answer = new Answer();
		 answer.setContent(content);
		 answer.setQuestion(question);
		 answer.setCreateDate(LocalDateTime.now());
		 answerRepository.save(answer);
	 }
	
}
