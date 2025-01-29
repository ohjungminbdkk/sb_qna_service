package com.sbs.qna_service.boundedContext.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 요청이 들어올 때만 객체를 생성한다.
@Controller
public class QuestionController {
	
	private final QuestionRepository questionRepository;
	
	@GetMapping("/question/list")
	// @ResponseBody 使わない理由、リターンされる”文字列”が表示される。
	public String list(Model model) {
	//return "requstion_list"; //Html名を指定。
		
        List<Question> questionList = questionRepository.findAll();
        model.addAttribute("questionList", questionList);
        return "question_list";
	}
	
}
