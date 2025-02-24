package com.mozip.mozip.domain.mozip.controller;

import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.service.ClubService;
import com.mozip.mozip.domain.mozip.dto.MozipRequestDto;
import com.mozip.mozip.domain.mozip.dto.MozipResponseDto;
import com.mozip.mozip.domain.mozip.dto.UpdateMozipStatusDto;
import com.mozip.mozip.domain.mozip.service.MozipManager;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.user.service.PositionService;
import com.mozip.mozip.global.dto.CustomUserDetails;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mozip")
@RequiredArgsConstructor
public class MozipController {

    private final MozipManager mozipManager;
    private final PositionService positionService;
    private final ClubService clubService;

    @GetMapping
    public Map<String,Object> getMozipsByClubId(Authentication authentication, @RequestParam("club_id") String clubId) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).user();
        Club club = clubService.getClubById(clubId);

        Map<String,Object> response = new HashMap<>();
        response.put("mozips", mozipManager.getMozipsByClubId(clubId));
        response.put("position", positionService.getPositionByUserAndClub(user, club).getPositionName());
        return response;
    }

    @GetMapping("/{mozip_id}")
    public MozipResponseDto getMozipById(@PathVariable("mozip_id") String mozipId) {
        return mozipManager.getMozipInfoById(mozipId);
    }

    @PostMapping
    public MozipResponseDto createMozip(
            @RequestParam("club_id") String clubId,
            @RequestBody MozipRequestDto requestDto) {
        return mozipManager.createMozip(clubId, requestDto);
    }

    @PutMapping("/{mozip_id}")
    public MozipResponseDto updateMozip(
            @PathVariable("mozip_id") String mozipId,
            @RequestBody MozipRequestDto requestDto) {
        return mozipManager.updateMozip(mozipId, requestDto.getTitle(), requestDto.getDescription());
    }

    @DeleteMapping("/{mozip_id}")
    public ResponseEntity<?> deleteMozip(@PathVariable("mozip_id") String mozipId) {
        mozipManager.deleteMozip(mozipId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{mozip_id}/status")
    public MozipResponseDto updateMozipStatus(
            @PathVariable("mozip_id") String mozipId,
            @RequestBody UpdateMozipStatusDto requestDto) {
        return mozipManager.updateMozipStatus(mozipId, requestDto.getStatus());
    }
}