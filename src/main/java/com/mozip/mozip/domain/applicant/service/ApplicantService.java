package com.mozip.mozip.domain.applicant.service;

import com.mozip.mozip.domain.PaperQuestion.dto.PaperQuestionWithAnswersData;
import com.mozip.mozip.domain.PaperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.PaperQuestion.repository.PaperQuestionRepository;
import com.mozip.mozip.domain.answer.dto.PaperAnswerData;
import com.mozip.mozip.domain.answer.dto.PaperAnswersResDto;
import com.mozip.mozip.domain.answer.entity.PaperAnswer;
import com.mozip.mozip.domain.answer.repository.PaperAnswerRepository;
import com.mozip.mozip.domain.applicant.dto.ApplicantListResponse;
import com.mozip.mozip.domain.applicant.dto.PaperApplicantData;
import com.mozip.mozip.domain.applicant.dto.UpdateApplicantStatusRequest;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.exception.ApplicantNotFoundException;
import com.mozip.mozip.domain.applicant.repository.ApplicantRepository;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluatedApplicantData;
import com.mozip.mozip.domain.evaluation.dto.PaperEvaluationData;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.evaluation.service.EvaluationService;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.mozip.service.MozipService;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final PaperQuestionRepository paperQuestionRepository;
    private final PaperAnswerRepository paperAnswerRepository;

    private final MozipService mozipService;
    private final EvaluationService evaluationService;

    private Applicant getApplicantById(String applicantId) {
        return applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ApplicantNotFoundException(applicantId));
    }

    public Double calculateAveragePaperScore(Applicant applicant) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByApplicant(applicant);
        OptionalDouble average = evaluations.stream()
                .mapToInt(Evaluation::getPaperScore)
                .average();
        return average.isPresent() ? Math.round(average.getAsDouble() * 100.0) / 100.0 : null;
    }


    // 서류 지원자 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<PaperApplicantData> getApplicantListByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantRepository.findApplicantsByMozip(mozip);
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
    public PaperAnswersResDto getPaperAnswersByMozipId(User evaluator, String mozipId, String applicantIds, String questionIds) {
        // questionId로 필터링
        List<PaperQuestion> paperQuestions = paperQuestionRepository.findByMozipId(mozipId).stream()
                .filter(question -> questionIds == null || Arrays.asList(questionIds.split(",")).contains(question.getId()))
                .toList();
        List<PaperQuestionWithAnswersData> questionWithAnswersDataList = paperQuestions.stream()
                .map(question -> {
                    // applicantId로 필터링
                    List<PaperAnswer> paperAnswers = paperAnswerRepository.findByQuestion(question)
                            .stream()
                            .filter(answer -> applicantIds == null || Arrays.asList(applicantIds.split(",")).contains(answer.getApplicant().getId()))
                            .toList();
                    List<PaperAnswerData> paperAnswerDataList = paperAnswers.stream()
                            .map(answer -> {
                                Evaluation evaluation = evaluationService.getEvaluationByApplicantAndEvaluator(answer.getApplicant(), evaluator);
                                return PaperAnswerData.from(answer, evaluation.getPaperScore());
                            })
                            .toList();
                    return PaperQuestionWithAnswersData.from(question, paperAnswerDataList);
                })
                .toList();
        return PaperAnswersResDto.from(questionWithAnswersDataList);
    }

    // 서류 합불 상태 수정
    @Transactional
    public void updateApplicantPaperStatuses(UpdateApplicantStatusRequest request) {
        request.getApplicants().forEach(each -> {
            Applicant applicant = getApplicantById(each.getApplicantId());
            applicant.setPaperStatus(each.getStatus());
            applicantRepository.save(applicant);
        });
    }

    // 서류 평가 점수 목록 조회
    @Transactional(readOnly = true)
    public ApplicantListResponse<PaperEvaluatedApplicantData> getPaperEvaluationsByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        List<Applicant> applicants = applicantRepository.findApplicantsByMozip(mozip);
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

    // 서류 합격자 목록 조회
    // @Transactional(readOnly = true)
    // public ApplicantListResponse<Interview>

    // 면접 기록 목록 조회

    // 면접 평가 점수 목록 조회

    // 특정 서류 응답 평가 조회

}