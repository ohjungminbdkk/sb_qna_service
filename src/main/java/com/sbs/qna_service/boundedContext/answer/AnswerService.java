package com.sbs.qna_service.boundedContext.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sbs.qna_service.boundedContext.DataNotFoundException;
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

	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}

	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = this.answerRepository.findById(id);
		if (answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}
	
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
}
