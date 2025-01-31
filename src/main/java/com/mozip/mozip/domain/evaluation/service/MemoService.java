package com.mozip.mozip.domain.evaluation.service;

import com.mozip.mozip.domain.evaluation.dto.MemoData;
import com.mozip.mozip.domain.evaluation.entity.InterviewMemo;
import com.mozip.mozip.domain.evaluation.entity.PaperMemo;
import com.mozip.mozip.domain.evaluation.exception.MemoNotFoundException;
import com.mozip.mozip.domain.evaluation.repository.InterviewMemoRepository;
import com.mozip.mozip.domain.evaluation.repository.PaperMemoRepository;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final PaperMemoRepository paperMemoRepository;
    private final InterviewMemoRepository interviewMemoRepository;

    public PaperMemo getPaperMemoByMemoId(String memoId) {
        return paperMemoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException(memoId));
    }

    public InterviewMemo getInterviewMemoByMemoId(String memoId) {
        return interviewMemoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException(memoId));
    }

    @Transactional(readOnly = true)
    public List<MemoData> getMemosByPaperAnswer(PaperAnswer paperAnswer) {
        List<PaperMemo> paperMemos = paperMemoRepository.findByPaperAnswer(paperAnswer);
        return paperMemos.stream()
                .map(MemoData::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MemoData> getMemosByInterviewAnswer(InterviewAnswer interviewAnswer) {
        List<InterviewMemo> interviewMemos = interviewMemoRepository.findByInterviewAnswer(interviewAnswer);
        return interviewMemos.stream()
                .map(MemoData::from)
                .toList();
    }

    public void savePaperMemo(PaperMemo paperMemo) {
        paperMemoRepository.save(paperMemo);
    }

    public void deletePaperMemo(PaperMemo paperMemo) {
        paperMemoRepository.delete(paperMemo);
    }

    public void saveInterviewMemo(InterviewMemo interviewMemo) {
        interviewMemoRepository.save(interviewMemo);
    }

    public void deleteInterviewMemo(InterviewMemo interviewMemo) {
        interviewMemoRepository.delete(interviewMemo);
    }
} 