package com.mozip.mozip.domain.applicant.service;

import com.mozip.mozip.domain.applicant.dto.ApplicantListResponse;
import com.mozip.mozip.domain.applicant.dto.InterviewApplicantData;
import com.mozip.mozip.domain.applicant.dto.PaperApplicantData;
import com.mozip.mozip.domain.applicant.dto.UpdateApplicantStatusRequest;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import com.mozip.mozip.domain.applicant.exception.ApplicantException;
import com.mozip.mozip.domain.evaluation.dto.InterviewEvaluatedApplicantData;
import com.mozip.mozip.domain.evaluation.dto.InterviewEvaluationData;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluatedApplicantData;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluationData;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.evaluation.service.EvaluationService;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswerForApplicantDto;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswersForApplicantResDto;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewAnswer.service.InterviewAnswerService;
import com.mozip.mozip.domain.interviewQuestion.dto.InterviewQuestionWithAnswersDto;
import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import com.mozip.mozip.domain.interviewQuestion.service.InterviewQuestionService;
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
import java.util.Comparator;
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
    private final InterviewQuestionService interviewQuestionService;
    private final InterviewAnswerService interviewAnswerService;

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

    public List<Applicant> getSortedApplicantsByMozip(Mozip mozip, String sortBy, String order) {
        List<Applicant> applicants = applicantService.getApplicantsByMozip(mozip);
        Comparator<Applicant> comparator;
        switch (sortBy) {
            case "realname":
                comparator = Comparator.comparing(applicant -> applicant.getUser().getRealname());
                break;
            case "applied_at":
                comparator = Comparator.comparing(Applicant::getCreatedAt);
                break;
            case "paper_score":
                comparator = Comparator.comparing(this::calculateAveragePaperScore);
                break;
            case "interview_score":
                comparator = Comparator.comparing(this::calculateAverageInterviewScore);
                break;
            case "paper_status":
                comparator = Comparator.comparing(Applicant::getPaperStatus);
                break;
            case "interview_status":
                comparator = Comparator.comparing(Applicant::getInterviewStatus);
                break;
            default:
                comparator = Comparator.comparing(Applicant::getApplicationNumber);
                break;
        }
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return applicants.stream().sorted(comparator).toList();
    }

    // 서류 지원자 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<PaperApplicantData> getApplicantListByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = getSortedApplicantsByMozip(mozip, sortBy, order);
        List<PaperApplicantData> applicantDataList = createApplicantDataList(applicants, applicant -> {
            Double paperScore = calculateAveragePaperScore(applicant);
            return PaperApplicantData.from(applicant, paperScore);
        });
        return ApplicantListResponse.from(applicantDataList);
    }

    private List<PaperQuestion> filterPaperQuestionsByIds(List<PaperQuestion> questions, String questionIds) {
        return questions.stream()
                .filter(question -> questionIds == null || Arrays.asList(questionIds.split(",")).contains(question.getId()))
                .toList();
    }

    private List<InterviewQuestion> filterInterviewQuestionsByIds(List<InterviewQuestion> questions, String questionIds) {
        return questions.stream()
                .filter(question -> questionIds == null || Arrays.asList(questionIds.split(",")).contains(question.getId()))
                .toList();
    }

    private List<PaperAnswer> filterPaperAnswersByApplicantIds(List<PaperAnswer> answers, String applicantIds) {
        return answers.stream()
                .filter(answer -> applicantIds == null || Arrays.asList(applicantIds.split(",")).contains(answer.getApplicant().getId()))
                .toList();
    }

    private List<InterviewAnswer> filterInterviewAnswersByApplicantIds(List<InterviewAnswer> answers, String applicantIds) {
        return answers.stream()
                .filter(answer -> applicantIds == null || Arrays.asList(applicantIds.split(",")).contains(answer.getApplicant().getId()))
                .toList();
    }

    // 서류 지원서 목록 조회
    @Transactional(readOnly = true)
    public PaperAnswersForApplicantResDto getPaperAnswersByMozipId(User evaluator, String mozipId, String applicantIds, String questionIds) {
        List<PaperQuestion> paperQuestions = filterPaperQuestionsByIds(paperQuestionService.getPaperQuestionsByMozipId(mozipId), questionIds);
        List<PaperQuestionWithAnswersDto> questionWithAnswersDataList = paperQuestions.stream()
                .map(question -> {
                    List<PaperAnswer> paperAnswers = filterPaperAnswersByApplicantIds(paperAnswerService.getPaperAnswersByQuestionId(question.getId()), applicantIds);
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

    // 서류 평가 점수 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<PaperEvaluatedApplicantData> getPaperEvaluationsByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = getSortedApplicantsByMozip(mozip, sortBy, order);
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

    // 서류 합격자 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<InterviewApplicantData> getInterviewApplicantListByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = getSortedApplicantsByMozip(mozip, sortBy, order);
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

    // 면접 기록 목록 조회
    @Transactional(readOnly = true)
    public InterviewAnswersForApplicantResDto getInterviewAnswersByMozipId(User evaluator, String mozipId, String applicantIds, String questionIds) {
        List<InterviewQuestion> interviewQuestions = filterInterviewQuestionsByIds(interviewQuestionService.getInterviewQuestionsByMozipId(mozipId), questionIds);
        List<InterviewQuestionWithAnswersDto> questionWithAnswersDataList = interviewQuestions.stream()
                .map(question -> {
                    List<InterviewAnswer> interviewAnswers = filterInterviewAnswersByApplicantIds(interviewAnswerService.getInterviewAnswersByQuestionId(question.getId()), applicantIds);
                    List<InterviewAnswerForApplicantDto> interviewAnswerDataList = interviewAnswers.stream()
                            .map(answer -> {
                                Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(answer.getApplicant(), evaluator);
                                return InterviewAnswerForApplicantDto.from(answer, evaluation.getInterviewScore());
                            })
                            .toList();
                    return InterviewQuestionWithAnswersDto.from(question, interviewAnswerDataList);
                })
                .toList();
        return InterviewAnswersForApplicantResDto.from(questionWithAnswersDataList);
    }

    // 면접 평가 점수 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<InterviewEvaluatedApplicantData> getInterviewEvaluationsByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = getSortedApplicantsByMozip(mozip, sortBy, order);
        List<InterviewEvaluatedApplicantData> applicantDataList = createApplicantDataList(applicants, applicant -> {
            List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
            List<InterviewEvaluationData> evaluationDataList = evaluations.stream()
                    .map(InterviewEvaluationData::from)
                    .toList();
            Double interviewScore = calculateAverageInterviewScore(applicant);
            Double paperScore = calculateAveragePaperScore(applicant);
            return InterviewEvaluatedApplicantData.from(applicant, paperScore, interviewScore, evaluationDataList);

        });
        return ApplicantListResponse.from(applicantDataList);
    }

    // 서류 합불 상태 수정
    @Transactional
    public void updateApplicantPaperStatuses(UpdateApplicantStatusRequest request) {
        request.getApplicants().forEach(each -> {
            Applicant applicant = applicantService.getApplicantById(each.getApplicantId());
            if (applicant.getPaperStatus() == EvaluationStatus.UNEVALUATED) {
                throw ApplicantException.paperNotEvaluated(applicant);
            }
            applicant.setPaperStatus(each.getStatus());
            applicantService.saveApplicant(applicant);
        });

    }

    // 면접 합불 상태 수정
    @Transactional
    public void updateApplicantInterviewStatuses(UpdateApplicantStatusRequest request) {
        request.getApplicants().forEach(each -> {
            Applicant applicant = applicantService.getApplicantById(each.getApplicantId());
            if (applicant.getInterviewStatus() == EvaluationStatus.UNEVALUATED) {
                throw ApplicantException.interviewNotEvaluated(applicant);
            }
            applicant.setInterviewStatus(each.getStatus());
            applicantService.saveApplicant(applicant);
        });

    }
}
