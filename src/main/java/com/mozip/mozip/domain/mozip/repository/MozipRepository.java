package com.mozip.mozip.domain.mozip.repository;

import com.mozip.mozip.domain.mozip.entity.Mozip;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MozipRepository extends JpaRepository<Mozip, String> {
    List<Mozip> findByClubId(String clubId);
}