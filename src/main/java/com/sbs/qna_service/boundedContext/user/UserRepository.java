package com.sbs.qna_service.boundedContext.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	@Modifying
	@Transactional
	@Query(value = "ALTER TABLE site_user AUTO_INCREMENT = 1", nativeQuery = true)
	void clearAutoIncrement();
}