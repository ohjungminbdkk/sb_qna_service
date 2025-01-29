package com.sbs.qna_service.boundedContext.question;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.sbs.qna_service.boundedContext.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepository;
	
	public List<Question> findAll(){
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
}
