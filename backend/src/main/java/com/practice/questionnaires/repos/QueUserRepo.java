package com.practice.questionnaires.repos;

import com.practice.questionnaires.models.QueUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueUserRepo extends JpaRepository<QueUser, Long> {
    @Query(value = "SELECT * FROM QUE_USER WHERE USERNAME = ?1", nativeQuery = true)
    Optional<QueUser> findByUsername(String username);

    @Query(value = "SELECT * FROM QUE_USER WHERE EMAIL = ?1", nativeQuery = true)
    Optional<QueUser> findByEmail(String email);

    @Query(value = "SELECT * FROM QUE_USER ORDER BY SCORE DESC", nativeQuery = true)
    Optional<List<QueUser>> findAllUsers();
}