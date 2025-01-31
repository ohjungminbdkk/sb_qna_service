package com.sbs.qna_service.boundedContext.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sbs.qna_service.boundedContext.question.Question;
import com.sbs.qna_service.boundedContext.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	 public Answer create(Question question, String content, SiteUser author) {
		 Answer answer = new Answer();
		 answer.setContent(content);
		 answer.setAuthor(author);
		 answer.setCreateDate(LocalDateTime.now());
		 question.addAnswer(answer);
		 answerRepository.save(answer);
		 
		 return answer;
	 }
	
}
