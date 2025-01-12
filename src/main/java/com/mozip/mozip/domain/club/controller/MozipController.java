package com.mozip.mozip.domain.club.controller;

import com.mozip.mozip.domain.club.dto.MozipRequestDto;
import com.mozip.mozip.domain.club.entity.Mozip;
import com.mozip.mozip.domain.club.service.MozipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mozip")
@RequiredArgsConstructor
public class MozipController {

    private final MozipService mozipService;

    @GetMapping
    public ResponseEntity<List<Mozip>> getMozipsByClubId(@RequestParam("club_id") String clubId) {
        return ResponseEntity.ok(mozipService.getMozipsByClubId(clubId));
    }

    @GetMapping("/{mozip_id}")
    public ResponseEntity<Mozip> getMozipById(@PathVariable("mozip_id") String mozipId) {
        return ResponseEntity.ok(mozipService.getMozipById(mozipId));
    }

    @PostMapping
    public ResponseEntity<Mozip> createMozip(
            @RequestParam("club_id") String clubId,
            @RequestBody MozipRequestDto requestDto) {
        Mozip createdMozip = mozipService.createMozip(clubId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMozip);
    }

    @PutMapping("/{mozip_id}")
    public ResponseEntity<Mozip> updateMozip(
            @PathVariable("mozip_id") String mozipId,
            @RequestBody MozipRequestDto requestDto) {
        Mozip updatedMozip = mozipService.updateMozip(mozipId, requestDto.getTitle(), requestDto.getDescription());
        return ResponseEntity.ok(updatedMozip);
    }

    @DeleteMapping("/{mozip_id}")
    public ResponseEntity<Void> deleteMozip(@PathVariable("mozip_id") String mozipId) {
        mozipService.deleteMozip(mozipId);
        return ResponseEntity.noContent().build();
    }
}