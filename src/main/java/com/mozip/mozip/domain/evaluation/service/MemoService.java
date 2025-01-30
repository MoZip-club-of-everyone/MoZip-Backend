package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.entity.PaperMemo;
import com.mozip.mozip.domain.evaluation.repository.PaperMemoRepository;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final PaperMemoRepository paperMemoRepository;

    public PaperMemo addPaperMemo(User evaluator, String paperAnswerId, String memo) {
        // Implement logic to add a paper memo
        return null;
    }

    public PaperMemo updatePaperMemo(User evaluator, String paperAnswerId, String memoId, String memo) {
        // Implement logic to update a paper memo
        return null;
    }

    public void deletePaperMemo(User evaluator, String paperAnswerId, String memoId) {
        // Implement logic to delete a paper memo
    }

    public PaperMemo addInterviewMemo(User evaluator, String interviewAnswerId, String memo) {
        // Implement logic to add an interview memo
        return null;
    }

    public PaperMemo updateInterviewMemo(User evaluator, String interviewAnswerId, String memoId, String memo) {
        // Implement logic to update an interview memo
        return null;
    }

    public void deleteInterviewMemo(User evaluator, String interviewAnswerId, String memoId) {
        // Implement logic to delete an interview memo
    }
} 