package com.mozip.mozip.domain.club.repository;

import com.mozip.mozip.domain.club.entity.Mozip;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MozipRepository extends JpaRepository<Mozip, String> {
    List<Mozip> findByClubId(String clubId);
}