package com.mozip.mozip.domain.user.repository;

import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.user.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, String> {
    List<Position> findByUserId(String userId);
    @Query("SELECT p.club FROM Position p WHERE p.user.id = :userId")
    List<Club> findClubsByUserId(@Param("userId") String userId);
}
