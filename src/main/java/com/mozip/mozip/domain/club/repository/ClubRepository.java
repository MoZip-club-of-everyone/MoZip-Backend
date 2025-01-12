package com.mozip.mozip.domain.club.repository;

import com.mozip.mozip.domain.club.entity.Club;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, String> {
    @EntityGraph(attributePaths = {"position", "mozip"})
    List<Club> findAll();

    boolean existsByName(String name);
}
