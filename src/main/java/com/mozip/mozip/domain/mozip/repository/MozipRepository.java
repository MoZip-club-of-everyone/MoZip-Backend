package com.mozip.mozip.domain.mozip.repository;

import com.mozip.mozip.domain.mozip.entity.Mozip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MozipRepository extends JpaRepository<Mozip, String> {
    List<Mozip> findByClubId(String clubId);

    List<Mozip> findByStartDateAfter(LocalDateTime date);
}