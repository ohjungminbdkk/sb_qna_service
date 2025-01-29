package com.sbs.qna_service.boundedContext.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	@Modifying //INSERT,UPDATE,DELETEのような、データ変更作業にて使用
	// nativeQuery = trueしか、MySQL Query使用可能
	@Transactional
	@Query(value = "ALTER TABLE answer AUTO_INCREMENT = 1", nativeQuery = true)
	void clearAutoIncrement();
}
