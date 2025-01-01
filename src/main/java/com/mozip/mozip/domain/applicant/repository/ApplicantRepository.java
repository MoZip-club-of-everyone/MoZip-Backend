package com.mozip.mozip.domain.applicant.repository;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.club.entity.Mozip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicantRepository {

    @Query("SELECT a FROM Applicant a " +
            "JOIN a.evaluations e " +
            "WHERE a.mozip = :mozip " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'realname' THEN a.user.realname " +
            "     WHEN :sortBy = 'applied_at' THEN a.createdAt " +
            "     WHEN :sortBy = 'paper_score' THEN e.paperScore " +
            "     ELSE a.applicationNumber END " +
            "ASC " +
            "CASE WHEN :order = 'desc' THEN DESC ELSE ASC END")
    List<Applicant> findByMozip(Mozip mozip, String sortBy, String order);
}