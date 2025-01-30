package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.entity.InterviewMemo;
import com.mozip.mozip.domain.evaluation.entity.PaperMemo;
import com.mozip.mozip.domain.evaluation.exception.MemoNotFoundException;
import com.mozip.mozip.domain.evaluation.repository.InterviewMemoRepository;
import com.mozip.mozip.domain.evaluation.repository.PaperMemoRepository;
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
public class MemoService {
    private final PaperMemoRepository paperMemoRepository;
    private final InterviewMemoRepository interviewMemoRepository;

    private final PaperAnswerService paperAnswerService;
    private final InterviewAnswerService interviewAnswerService;

    private PaperMemo getPaperMemoByMemoId(String memoId) {
        return paperMemoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException(memoId));
    }

    private InterviewMemo getInterviewMemoByMemoId(String memoId) {
        return interviewMemoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException(memoId));
    }

    @Transactional
    public void addPaperMemo(User evaluator, String paperAnswerId, String memo) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerById(paperAnswerId);
        PaperMemo paperMemo = PaperMemo.builder()
                .writer(evaluator)
                .paperAnswer(paperAnswer)
                .memo(memo)
                .build();
        paperMemoRepository.save(paperMemo);
    }

    @Transactional
    public void updatePaperMemo(User evaluator, String paperAnswerId, String memoId, String memo) {
        PaperMemo paperMemo = getPaperMemoByMemoId(memoId);
        paperMemo.setMemo(memo);
        paperMemoRepository.save(paperMemo);
    }

    @Transactional
    public void deletePaperMemo(User evaluator, String paperAnswerId, String memoId) {
        PaperMemo paperMemo = getPaperMemoByMemoId(memoId);
        paperMemoRepository.delete(paperMemo);
    }

    @Transactional
    public void addInterviewMemo(User evaluator, String interviewAnswerId, String memo) {
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerById(interviewAnswerId);
        InterviewMemo interviewMemo = InterviewMemo.builder()
                .writer(evaluator)
                .interviewAnswer(interviewAnswer)
                .memo(memo)
                .build();
        interviewMemoRepository.save(interviewMemo);
    }

    @Transactional
    public void updateInterviewMemo(User evaluator, String interviewAnswerId, String memoId, String memo) {
        InterviewMemo interviewMemo = getInterviewMemoByMemoId(memoId);
        interviewMemo.setMemo(memo);
        interviewMemoRepository.save(interviewMemo);
    }

    @Transactional
    public void deleteInterviewMemo(User evaluator, String interviewAnswerId, String memoId) {
        InterviewMemo interviewMemo = getInterviewMemoByMemoId(memoId);
        interviewMemoRepository.delete(interviewMemo);
    }
} 