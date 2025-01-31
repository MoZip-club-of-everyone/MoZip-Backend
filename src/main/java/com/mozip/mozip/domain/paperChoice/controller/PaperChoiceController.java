package com.mozip.mozip.domain.paperChoice.controller;

import com.mozip.mozip.domain.paperChoice.dto.PaperChoiceReqDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoiceResDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoicesResDto;
import com.mozip.mozip.domain.paperChoice.dto.PaperChoicesReqDto;
import com.mozip.mozip.domain.paperChoice.service.PaperChoiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/papers/choices")
public class PaperChoiceController {
    private final PaperChoiceManager paperChoiceManager;

    @GetMapping("/{choice_id}")
    public PaperChoiceResDto getChoiceById(@PathVariable("choice_id") String choiceId) {
        return paperChoiceManager.getPaperChoiceById(choiceId);
    }

    @PutMapping("/{choice_id}")
    public PaperChoiceResDto updateChoice(@PathVariable("choice_id") String choiceId, @RequestBody PaperChoiceReqDto dto) {
        return paperChoiceManager.updatePaperChoice(choiceId, dto);
    }

    @PutMapping
    public PaperChoicesResDto updateChoices(@RequestBody PaperChoicesReqDto dto) {
        return paperChoiceManager.updatePaperChoices(dto);
    }

    @DeleteMapping("/{choice_id}")
    public void deleteChoice(@PathVariable("choice_id") String choiceId) {
        paperChoiceManager.deletePaperChoice(choiceId);
    }
}
