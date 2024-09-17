package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
