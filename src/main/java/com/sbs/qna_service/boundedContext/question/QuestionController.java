package com.sbs.qna_service.boundedContext.question;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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
    public String questionCreate(@ModelAttribute QuestionForm questionForm) {//@ModelAttribute 명시안해도 됨.
        return "question_form";
    }
    
	/*
	 * 아래는 폼을 수동관리하기 용이함.
	 * @GetMapping(value = "/create") public String questionCreate(Model model) {
	 * model.addAttribute("question", new QuestionForm()); return "question_form"; }
	 */
    
    @PostMapping(value = "/create")
    //Valid QuestionForm
    //questionForm값을 바인딩 할 때 유효성 체크를 해라
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
    	if(bindingResult.hasErrors()) {
    		//question_form.html 실행
    		//다시 작성하라는 의미로 응답에 폼을 실어서 보냄
    		return "question_form";
    	}
    	questionService.create(questionForm.getSubject(), questionForm.getContent());
    	return "redirect:/question/list";
    }
}
