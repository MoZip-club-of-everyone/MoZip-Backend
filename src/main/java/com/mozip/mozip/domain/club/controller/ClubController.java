package com.mozip.mozip.domain.club.controller;

import com.mozip.mozip.domain.club.dto.ClubCreateReqDto;
import com.mozip.mozip.domain.club.dto.ClubHomeResDto;
import com.mozip.mozip.domain.club.dto.ClubResponseDto;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubController {
    private final ClubService clubService;

    @GetMapping("/club/{club_id}")
    public ResponseEntity<Club> getClubById(@PathVariable("Club_id") String clubId) {
        return ResponseEntity.ok(clubService.getClubById(clubId));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<ClubHomeResDto>> getClubsByUserId(@PathVariable("user_id") String userId) {
        return ResponseEntity.ok(clubService.getClubsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Club> createClub(
            @RequestBody ClubCreateReqDto requestDto) {
        Club createdClub = clubService.createClub(requestDto.getName(), requestDto.getImage());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClub);
    }

    @PutMapping("/{club_id}")
    public ResponseEntity<ClubResponseDto> updateClub(
            @PathVariable("club_id") String clubId,
            @RequestBody ClubCreateReqDto requestDto) {
        ClubResponseDto updateClub = clubService.updateClub(clubId, requestDto.getName(), requestDto.getImage());
        return ResponseEntity.ok(updateClub);
    }

    @DeleteMapping("/{club_id}")
    public ResponseEntity<Void> deleteClub(@PathVariable("club_id") String clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.noContent().build();
    }
}
