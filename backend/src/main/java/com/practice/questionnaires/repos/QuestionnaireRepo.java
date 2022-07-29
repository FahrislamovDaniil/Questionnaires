package com.practice.questionnaires.repos;

import com.practice.questionnaires.models.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireRepo extends JpaRepository<Questionnaire, Long> {
    @Query(value = "SELECT * FROM QUESTIONNAIRE WHERE ID = ?1", nativeQuery = true)
    Optional<Questionnaire> findQuestionnaireById(Long questionnaire);

    @Query(value = "SELECT * FROM QUESTIONNAIRE WHERE NAME = ?1", nativeQuery = true)
    Optional<Questionnaire> findByName(String name);

    @Query(value = "SELECT * FROM QUESTIONNAIRE", nativeQuery = true)
    Optional<List<Questionnaire>> findAllQuestionnaires();
}