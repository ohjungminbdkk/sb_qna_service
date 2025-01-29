package com.sbs.qna_service.boundedContext.question;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepository;
	
	public List<Question> findAll(){
		return questionRepository.findAll();
	}
	
}
