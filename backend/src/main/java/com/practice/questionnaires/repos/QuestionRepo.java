package com.practice.questionnaires.repos;

import com.practice.questionnaires.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM QUESTION WHERE ID = ?1", nativeQuery = true)
    Optional<Question> getQuestionById(Long question);
}
