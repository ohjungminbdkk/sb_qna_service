package com.sbs.qna_service.boundedContext.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer>{//QuestionエンティティのPKEYがIntegerなので、指定する。

}
