package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.entity.InterviewMemo;
import com.mozip.mozip.domain.evaluation.entity.PaperMemo;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewAnswer.service.InterviewAnswerService;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperAnswer.service.PaperAnswerService;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoManager {
    private final MemoService memoService;
    private final PaperAnswerService paperAnswerService;
    private final InterviewAnswerService interviewAnswerService;

    @Transactional
    public void addPaperMemo(User evaluator, String paperAnswerId, String memo) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerById(paperAnswerId);
        PaperMemo paperMemo = PaperMemo.builder()
                .writer(evaluator)
                .paperAnswer(paperAnswer)
                .memo(memo)
                .build();
        memoService.savePaperMemo(paperMemo);
    }

    @Transactional
    public void updatePaperMemo(User evaluator, String paperAnswerId, String memoId, String memo) {
        PaperMemo paperMemo = memoService.getPaperMemoByMemoId(memoId);
        paperMemo.setMemo(memo);
        memoService.savePaperMemo(paperMemo);
    }

    @Transactional
    public void deletePaperMemo(User evaluator, String paperAnswerId, String memoId) {
        PaperMemo paperMemo = memoService.getPaperMemoByMemoId(memoId);
        memoService.deletePaperMemo(paperMemo);
    }

    @Transactional
    public void addInterviewMemo(User evaluator, String interviewAnswerId, String memo) {
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerById(interviewAnswerId);
        InterviewMemo interviewMemo = InterviewMemo.builder()
                .writer(evaluator)
                .interviewAnswer(interviewAnswer)
                .memo(memo)
                .build();
        memoService.saveInterviewMemo(interviewMemo);
    }

    @Transactional
    public void updateInterviewMemo(User evaluator, String interviewAnswerId, String memoId, String memo) {
        InterviewMemo interviewMemo = memoService.getInterviewMemoByMemoId(memoId);
        interviewMemo.setMemo(memo);
        memoService.saveInterviewMemo(interviewMemo);
    }

    @Transactional
    public void deleteInterviewMemo(User evaluator, String interviewAnswerId, String memoId) {
        InterviewMemo interviewMemo = memoService.getInterviewMemoByMemoId(memoId);
        memoService.deleteInterviewMemo(interviewMemo);
    }
}
