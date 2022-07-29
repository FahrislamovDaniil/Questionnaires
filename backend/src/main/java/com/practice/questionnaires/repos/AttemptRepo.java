package com.practice.questionnaires.repos;

import com.practice.questionnaires.models.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepo extends JpaRepository<Attempt, Long> {
}
