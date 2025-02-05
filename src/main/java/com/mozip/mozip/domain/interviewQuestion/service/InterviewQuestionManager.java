package com.mozip.mozip.domain.interviewQuestion.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.service.ApplicantService;
import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionCreateReqDto;
import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionResDto;
import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionUpdateReqDto;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.mozip.service.MozipService;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewQuestionManager {

    private final InterviewQuestionService interviewQuestionService;
    private final MozipService mozipService;
    private final UserService userService;
    private final ApplicantService applicantService;

    public List<InterviewQuestionResDto> getInterviewQuestionsByMozipId(String mozipId) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        User user = userService.getCurrentUser();
        Applicant applicant = applicantService.getCurrentApplicant(user, mozip);
        return interviewQuestionService.getInterviewQuestionsByMozipId(mozipId)
                .stream().map(InterviewQuestionResDto::fromEntity).toList();
    }

    public InterviewQuestionResDto getInterviewQuestionById(String questionId) {
        return InterviewQuestionResDto.fromEntity(interviewQuestionService.getInterviewQuestionById(questionId));
    }

    public InterviewQuestionResDto createInterviewQuestion(InterviewQuestionCreateReqDto requestDto) {
        Mozip mozip = mozipService.getMozipById(requestDto.getMozipId());
        return InterviewQuestionResDto.fromEntity(interviewQuestionService.createInterviewQuestion(mozip, requestDto));
    }

    public InterviewQuestionResDto updateInterviewQuestion(String questionId, InterviewQuestionUpdateReqDto requestDto) {
        return InterviewQuestionResDto.fromEntity(interviewQuestionService.updateInterviewQuestion(questionId, requestDto));
    }

    public void deleteInterviewQuestion(String questionId) {
        interviewQuestionService.deleteInterviewQuestion(questionId);
    }
}