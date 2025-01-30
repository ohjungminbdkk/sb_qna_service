package com.sbs.qna_service.boundedContext.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor // 요청이 들어올 때만 객체를 생성한다.
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/list")
	// @ResponseBody 使わない理由、リターンされる”文字列”が表示される。
	public String list(Model model) {
	//return "requstion_list"; //Html名を指定。
		
        List<Question> questionList = questionService.findAll();
        model.addAttribute("questionList", questionList);
        return "question_list";
	}
	
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
    
	/*
	 * 또 다른 방식
	 * @GetMapping(value = "/question/detail") public String detail(Model
	 * model, @PathVariable("id") Integer id, @RequestParam int id) { return
	 * "question_detail"; }
	 */
    
    @GetMapping(value = "/create")
    public String create() {
        return "question_form";
    }
    
    @PostMapping(value = "/create")
    public String questionCreate(@RequestParam(value="subject") String subject, @RequestParam(value="content") String content){
    	questionService.create(subject, content);
    	return "redirect:/question/list";
    }
}
