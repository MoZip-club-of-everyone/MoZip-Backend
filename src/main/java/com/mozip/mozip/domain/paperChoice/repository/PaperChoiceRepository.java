package com.mozip.mozip.domain.paperChoice.repository;

import com.mozip.mozip.domain.paperChoice.entity.PaperChoice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperChoiceRepository extends JpaRepository<PaperChoice, String> {
    public List<PaperChoice> findByPaperQuestionId(String paperQuestionId);
}
