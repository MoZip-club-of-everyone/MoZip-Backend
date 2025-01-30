package com.mozip.mozip.domain.applicant.service;

import com.mozip.mozip.domain.applicant.dto.ApplicantListResponse;
import com.mozip.mozip.domain.applicant.dto.InterviewApplicantData;
import com.mozip.mozip.domain.applicant.dto.PaperApplicantData;
import com.mozip.mozip.domain.applicant.dto.UpdateApplicantStatusRequest;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import com.mozip.mozip.domain.evaluation.dto.InterviewEvaluatedApplicantData;
import com.mozip.mozip.domain.evaluation.dto.InterviewEvaluationData;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluatedApplicantData;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluationData;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.evaluation.service.EvaluationService;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.mozip.service.MozipService;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerForApplicantDto;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswersForApplicantResDto;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperAnswer.repository.PaperAnswerRepository;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionWithAnswersDto;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.paperQuestion.repository.PaperQuestionRepository;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class ApplicantManager {
    private final ApplicantService applicantService;
    private final MozipService mozipService;
    private final EvaluationService evaluationService;

    private final PaperQuestionRepository paperQuestionRepository;
    private final PaperAnswerRepository paperAnswerRepository;

    public Double calculateAveragePaperScore(Applicant applicant) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
        OptionalDouble average = evaluations.stream()
                .mapToInt(Evaluation::getPaperScore)
                .average();
        return average.isPresent() ? Math.round(average.getAsDouble() * 100.0) / 100.0 : null;
    }

    private Double calculateAverageInterviewScore(Applicant applicant) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
        OptionalDouble average = evaluations.stream()
                .mapToInt(Evaluation::getInterviewScore)
                .average();
        return average.isPresent() ? Math.round(average.getAsDouble() * 100.0) / 100.0 : null;
    }

    // 서류 지원자 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<PaperApplicantData> getApplicantListByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        List<PaperApplicantData> applicantDataList = applicants.stream()
                .map(applicant -> {
                    Double paperScore = calculateAveragePaperScore(applicant);
                    return PaperApplicantData.from(applicant, paperScore);
                })
                .toList();
        return ApplicantListResponse.from(applicantDataList);
    }



    // 서류 지원서 목록 조회
    @Transactional(readOnly = true)
    public PaperAnswersForApplicantResDto getPaperAnswersByMozipId(User evaluator, String mozipId, String applicantIds, String questionIds) {
        // questionId로 필터링
        List<PaperQuestion> paperQuestions = paperQuestionRepository.findByMozipId(mozipId).stream()
                .filter(question -> questionIds == null || Arrays.asList(questionIds.split(",")).contains(question.getId()))
                .toList();
        List<PaperQuestionWithAnswersDto> questionWithAnswersDataList = paperQuestions.stream()
                .map(question -> {
                    // applicantId로 필터링
                    List<PaperAnswer> paperAnswers = paperAnswerRepository.findByPaperQuestionId(question.getId())
                            .stream()
                            .filter(answer -> applicantIds == null || Arrays.asList(applicantIds.split(",")).contains(answer.getApplicant().getId()))
                            .toList();
                    List<PaperAnswerForApplicantDto> paperAnswerDataList = paperAnswers.stream()
                            .map(answer -> {
                                Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(answer.getApplicant(), evaluator);
                                return PaperAnswerForApplicantDto.from(answer, evaluation.getPaperScore());
                            })
                            .toList();
                    return PaperQuestionWithAnswersDto.from(question, paperAnswerDataList);
                })
                .toList();
        return PaperAnswersForApplicantResDto.from(questionWithAnswersDataList);
    }

    // 서류 합불 상태 수정
    @Transactional
    public void updateApplicantPaperStatuses(UpdateApplicantStatusRequest request) {
        request.getApplicants().forEach(each -> {
            Applicant applicant = applicantService.getApplicantById(each.getApplicantId());
            applicant.setPaperStatus(each.getStatus());
            applicantService.saveApplicant(applicant);
        });
    }

    // 서류 평가 점수 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<PaperEvaluatedApplicantData> getPaperEvaluationsByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        List<PaperEvaluatedApplicantData> applicantDataList = applicants.stream()
                .map(applicant -> {
                    List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
                    List<PaperEvaluationData> evaluationDataList = evaluations.stream()
                            .map(PaperEvaluationData::from)
                            .toList();
                    Double paperScore = calculateAveragePaperScore(applicant);
                    return PaperEvaluatedApplicantData.from(applicant, paperScore, evaluationDataList);
                })
                .toList();
        return ApplicantListResponse.from(applicantDataList);
    }

    // 면접 지원자 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<InterviewApplicantData> getInterviewApplicantListByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        List<InterviewApplicantData> applicantDataList = applicants.stream()
                .filter(applicant -> applicant.getPaperStatus() == EvaluationStatus.PASSED)
                .map(applicant -> {
                    Double interviewScore = calculateAverageInterviewScore(applicant);
                    Double paperScore = calculateAveragePaperScore(applicant);
                    return InterviewApplicantData.from(applicant, paperScore, interviewScore);
                })
                .toList();
        return ApplicantListResponse.from(applicantDataList);
    }

    // 면접 기록 목록 조회

    // 면접 평가 점수 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<InterviewEvaluatedApplicantData> getInterviewEvaluationsByMozipId(String mozipId, String sortBy, String order)  {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        List<InterviewEvaluatedApplicantData> applicantDataList = applicants.stream()
                .map(applicant -> {
                    List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
                    List<InterviewEvaluationData> evaluationDataList = evaluations.stream()
                            .map(InterviewEvaluationData::from)
                            .toList();
                    Double paperScore = calculateAveragePaperScore(applicant);
                    return InterviewEvaluatedApplicantData.from(applicant, paperScore, evaluationDataList);
                })
                .toList();
        return ApplicantListResponse.from(applicantDataList);
    }
}
