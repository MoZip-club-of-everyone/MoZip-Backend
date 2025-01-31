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
import com.mozip.mozip.domain.paperAnswer.service.PaperAnswerService;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionWithAnswersDto;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.paperQuestion.service.PaperQuestionService;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.ToIntFunction;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ApplicantManager {
    private final ApplicantService applicantService;
    private final MozipService mozipService;
    private final EvaluationService evaluationService;
    private final PaperQuestionService paperQuestionService;
    private final PaperAnswerService paperAnswerService;

    private Double calculateAverageScore(Applicant applicant, ToIntFunction<Evaluation> scoreFunction) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
        OptionalDouble average = evaluations.stream()
                .mapToInt(scoreFunction)
                .average();
        return average.isPresent() ? Math.round(average.getAsDouble() * 100.0) / 100.0 : null;
    }

    public Double calculateAveragePaperScore(Applicant applicant) {
        return calculateAverageScore(applicant, Evaluation::getPaperScore);
    }

    private Double calculateAverageInterviewScore(Applicant applicant) {
        return calculateAverageScore(applicant, Evaluation::getInterviewScore);
    }

    private <T> List<T> createApplicantDataList(List<Applicant> applicants, Function<Applicant, T> mapper) {
        return applicants.stream()
                .map(mapper)
                .toList();
    }

    @Transactional(readOnly = true)
    public ApplicantListResponse<PaperApplicantData> getApplicantListByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        List<PaperApplicantData> applicantDataList = createApplicantDataList(applicants, applicant -> {
            Double paperScore = calculateAveragePaperScore(applicant);
            return PaperApplicantData.from(applicant, paperScore);
        });
        return ApplicantListResponse.from(applicantDataList);
    }

    private List<PaperQuestion> filterQuestionsByIds(List<PaperQuestion> questions, String questionIds) {
        return questions.stream()
                .filter(question -> questionIds == null || Arrays.asList(questionIds.split(",")).contains(question.getId()))
                .toList();
    }

    private List<PaperAnswer> filterAnswersByApplicantIds(List<PaperAnswer> answers, String applicantIds) {
        return answers.stream()
                .filter(answer -> applicantIds == null || Arrays.asList(applicantIds.split(",")).contains(answer.getApplicant().getId()))
                .toList();
    }

    @Transactional(readOnly = true)
    public PaperAnswersForApplicantResDto getPaperAnswersByMozipId(User evaluator, String mozipId, String applicantIds, String questionIds) {
        List<PaperQuestion> paperQuestions = filterQuestionsByIds(paperQuestionService.getPaperQuestionsByMozipId(mozipId), questionIds);
        List<PaperQuestionWithAnswersDto> questionWithAnswersDataList = paperQuestions.stream()
                .map(question -> {
                    List<PaperAnswer> paperAnswers = filterAnswersByApplicantIds(paperAnswerService.getPaperAnswersByQuestionId(question.getId()), applicantIds);
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

    @Transactional(readOnly = true)
    public ApplicantListResponse<PaperEvaluatedApplicantData> getPaperEvaluationsByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        List<PaperEvaluatedApplicantData> applicantDataList = createApplicantDataList(applicants, applicant -> {
            List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
            List<PaperEvaluationData> evaluationDataList = evaluations.stream()
                    .map(PaperEvaluationData::from)
                    .toList();
            Double paperScore = calculateAveragePaperScore(applicant);
            return PaperEvaluatedApplicantData.from(applicant, paperScore, evaluationDataList);
        });
        return ApplicantListResponse.from(applicantDataList);
    }

    @Transactional(readOnly = true)
    public ApplicantListResponse<InterviewApplicantData> getInterviewApplicantListByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        List<InterviewApplicantData> applicantDataList = createApplicantDataList(
                applicants.stream().filter(applicant -> applicant.getPaperStatus() == EvaluationStatus.PASSED).toList(),
                applicant -> {
                    Double interviewScore = calculateAverageInterviewScore(applicant);
                    Double paperScore = calculateAveragePaperScore(applicant);
                    return InterviewApplicantData.from(applicant, paperScore, interviewScore);
                }
        );
        return ApplicantListResponse.from(applicantDataList);
    }

    @Transactional(readOnly = true)
    public ApplicantListResponse<InterviewEvaluatedApplicantData> getInterviewEvaluationsByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        List<InterviewEvaluatedApplicantData> applicantDataList = createApplicantDataList(applicants, applicant -> {
            List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
            List<InterviewEvaluationData> evaluationDataList = evaluations.stream()
                    .map(InterviewEvaluationData::from)
                    .toList();
            Double paperScore = calculateAveragePaperScore(applicant);
            return InterviewEvaluatedApplicantData.from(applicant, paperScore, evaluationDataList);
        });
        return ApplicantListResponse.from(applicantDataList);
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

    // 면접 합불 상태 수정
    @Transactional
    public void updateApplicantInterviewStatuses(UpdateApplicantStatusRequest request) {
        request.getApplicants().forEach(each -> {
            Applicant applicant = applicantService.getApplicantById(each.getApplicantId());
            applicant.setInterviewStatus(each.getStatus());
            applicantService.saveApplicant(applicant);
        });
    }
}
