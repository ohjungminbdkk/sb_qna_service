package com.sbs.qna_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sbs.qna_service.boundedContext.question.Question;

@SpringBootApplication
public class QnaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QnaServiceApplication.class, args);

	}

}
