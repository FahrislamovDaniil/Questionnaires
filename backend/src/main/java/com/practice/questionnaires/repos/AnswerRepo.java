package com.practice.questionnaires.repos;

import com.practice.questionnaires.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Long> {
    @Query(value = "SELECT * FROM ANSWER WHERE ID = ?1", nativeQuery = true)
    Optional<Answer> findAnswerById(Long id);
}
