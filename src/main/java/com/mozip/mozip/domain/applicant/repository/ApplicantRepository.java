package com.mozip.mozip.domain.applicant.repository;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, String> {

    List<Applicant> findAllByMozip(Mozip mozip);

    Optional<Applicant> findTopByMozipOrderByApplicationNumberDesc(Mozip mozip);

    Optional<Applicant> findByUserAndMozip(User user, Mozip mozip);

    //    정렬 로직 추후 도입
//    @Query("SELECT a FROM Applicant a " +
//            "JOIN a.evaluations e " +
//            "WHERE a.mozip = :mozip " +
//            "ORDER BY " +
//            "CASE WHEN :sortBy = 'realname' THEN a.user.realname " +
//            "     WHEN :sortBy = 'applied_at' THEN a.createdAt " +
//            "     WHEN :sortBy = 'paper_score' THEN e.paperScore " +
//            "     ELSE a.applicationNumber END " +
//            "ASC " +
//            "CASE WHEN :order = 'desc' THEN DESC ELSE ASC END")
//    List<Applicant> findApplicantsByMozipWithSorting(Mozip mozip, String sortBy, String order);
}