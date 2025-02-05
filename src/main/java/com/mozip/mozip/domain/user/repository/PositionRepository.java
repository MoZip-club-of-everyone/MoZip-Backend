package com.mozip.mozip.domain.user.repository;

import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.user.entity.Position;
import com.mozip.mozip.domain.user.entity.enums.PositionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, String> {
    List<Position> findByUserId(String userId);

    Optional<Position> findByUserIdAndClubId(String userId, String clubId);

    @Query("SELECT p.club FROM Position p WHERE p.user.id = :userId")
    List<Club> findClubsByUserId(@Param("userId") String userId);

    long countByClubAndPositionNameIn(Club club, List<PositionType> positionNames);

}
