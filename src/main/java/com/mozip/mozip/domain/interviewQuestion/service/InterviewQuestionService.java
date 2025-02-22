package com.mozip.mozip.domain.interviewQuestion.service;

import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionCreateReqDto;
import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionUpdateReqDto;
import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import com.mozip.mozip.domain.interviewQuestion.repository.InterviewQuestionRepository;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewQuestionService {

    private final InterviewQuestionRepository interviewQuestionRepository;

    public List<InterviewQuestion> getInterviewQuestionsByMozipId(String mozipId) {
        return interviewQuestionRepository.findByMozipId(mozipId);
    }

    public InterviewQuestion getInterviewQuestionById(String questionId) {
        return interviewQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("InterviewQuestion이 없습니다 : " + questionId));
    }

    @Transactional
    public InterviewQuestion createInterviewQuestion(Mozip mozip, InterviewQuestionCreateReqDto requestDto) {
        InterviewQuestion interviewQuestion = InterviewQuestion.builder()
                .mozip(mozip)
                .question(requestDto.getQuestion())
                .details(requestDto.getDetails())
                .isRequired(requestDto.isRequired())
                .build();
        return interviewQuestionRepository.save(interviewQuestion);
    }

    @Transactional
    public InterviewQuestion updateInterviewQuestion(String questionId, InterviewQuestionUpdateReqDto requestDto) {
        InterviewQuestion question = getInterviewQuestionById(questionId);
        question.updateQuestion(requestDto.getQuestion(), requestDto.getDetails(), requestDto.isRequired());
        return interviewQuestionRepository.save(question);
    }

    @Transactional
    public void deleteInterviewQuestion(String questionId) {
        InterviewQuestion existingQuestion = getInterviewQuestionById(questionId);
        interviewQuestionRepository.delete(existingQuestion);
    }

    public InterviewQuestion dtoToEntity(Mozip mozip, InterviewQuestionCreateReqDto requestDto){
        return InterviewQuestion.builder()
                .mozip(mozip)
                .question(requestDto.getQuestion())
                .details(requestDto.getDetails())
                .isRequired(requestDto.isRequired())
                .build();
    }

    public List<InterviewQuestion> getInterviewQuestionsByMozipOrQuestionIds(Mozip mozip, List<String> questionIds) {
        if (questionIds == null) {
            return interviewQuestionRepository.findByMozipId(mozip.getId());
        }
        return interviewQuestionRepository.findByIdIn(questionIds);
    }
}