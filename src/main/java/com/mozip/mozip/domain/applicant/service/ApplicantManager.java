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
import com.mozip.mozip.domain.user.entity.Position;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.applicant.dto.ApplicantInfoRequest;
import com.mozip.mozip.domain.applicant.dto.ApplicantInfoResponse;
import com.mozip.mozip.domain.user.exception.PositionException;
import com.mozip.mozip.domain.user.service.PositionService;
import com.mozip.mozip.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    private final UserService userService;
    private final PositionService positionService;

    // 지원자 생성
    @Transactional
    public ApplicantInfoResponse createApplicantByMozipId(ApplicantInfoRequest request, String mozipId) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        User user = userService.getCurrentUser();
        userService.updateApplicantUserInfo(user, request);
        Applicant applicant = applicantService.createApplicant(user, mozip);
        return ApplicantInfoResponse.from(applicant);
    }

    // 지원자 필수 정보 조회
    @Transactional(readOnly = true)
    public ApplicantInfoResponse getApplicantInfoByMozipId(User user, String mozipId) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        Applicant applicant = applicantService.getCurrentApplicant(user, mozip);
        return ApplicantInfoResponse.from(applicant);
    }

    private void checkReadable(User evaluator, Mozip mozip) {
        Position position = positionService.getPositionByUserAndClub(evaluator, mozip.getClub());
        if (!positionService.checkReadablePosition(position)) {
            throw PositionException.notReadable(position);
        }
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
                comparator = Comparator.comparing(Applicant::getPaperScoreAverage);
                break;
            case "interview_score":
                comparator = Comparator.comparing(Applicant::getInterviewScoreAverage);
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
    public ApplicantListResponse<PaperApplicantData> getApplicantListByMozipId(User evaluator, String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        checkReadable(evaluator, mozip);
        List<Applicant> applicants = getSortedApplicantsByMozip(mozip, sortBy, order);
        List<PaperApplicantData> applicantDataList = createApplicantDataList(applicants, PaperApplicantData::from);
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
        Mozip mozip = mozipService.getMozipById(mozipId);
        checkReadable(evaluator, mozip);
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
    public ApplicantListResponse<PaperEvaluatedApplicantData> getPaperEvaluationsByMozipId(User evaluator, String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        checkReadable(evaluator, mozip);
        List<Applicant> applicants = getSortedApplicantsByMozip(mozip, sortBy, order);
        List<PaperEvaluatedApplicantData> applicantDataList = createApplicantDataList(applicants, applicant -> {
            List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
            List<PaperEvaluationData> evaluationDataList = evaluations.stream()
                    .map(PaperEvaluationData::from)
                    .toList();
            return PaperEvaluatedApplicantData.from(applicant, evaluationDataList);
        });
        return ApplicantListResponse.from(applicantDataList);
    }

    // 서류 합격자 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<InterviewApplicantData> getInterviewApplicantListByMozipId(User evaluator, String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        checkReadable(evaluator, mozip);
        List<Applicant> applicants = getSortedApplicantsByMozip(mozip, sortBy, order);
        List<InterviewApplicantData> applicantDataList = createApplicantDataList(
                applicants.stream().filter(applicant -> applicant.getPaperStatus() == EvaluationStatus.PASSED).toList(),
                InterviewApplicantData::from
        );
        return ApplicantListResponse.from(applicantDataList);
    }

    // 면접 기록 목록 조회
    @Transactional(readOnly = true)
    public InterviewAnswersForApplicantResDto getInterviewAnswersByMozipId(User evaluator, String mozipId, String applicantIds, String questionIds) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        checkReadable(evaluator, mozip);
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
    public ApplicantListResponse<InterviewEvaluatedApplicantData> getInterviewEvaluationsByMozipId(User evaluator, String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        checkReadable(evaluator, mozip);
        List<Applicant> applicants = getSortedApplicantsByMozip(mozip, sortBy, order);
        List<InterviewEvaluatedApplicantData> applicantDataList = createApplicantDataList(applicants, applicant -> {
            List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
            List<InterviewEvaluationData> evaluationDataList = evaluations.stream()
                    .map(InterviewEvaluationData::from)
                    .toList();
            return InterviewEvaluatedApplicantData.from(applicant, evaluationDataList);


        });
        return ApplicantListResponse.from(applicantDataList);
    }
}
