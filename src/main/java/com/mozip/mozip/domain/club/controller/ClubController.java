package com.mozip.mozip.domain.club.controller;

import com.mozip.mozip.domain.club.dto.ClubHomeResDto;
import com.mozip.mozip.domain.club.dto.ClubResponseDto;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
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
    public ResponseEntity<?> createClub(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image) {
        try {
            log.info(name);
            ClubResponseDto createdClub = clubService.createClub(name, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClub);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{club_id}")
    public ResponseEntity<ClubResponseDto> updateClub(
            @PathVariable("club_id") String clubId,
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image) {
        ClubResponseDto updateClub = clubService.updateClub(clubId, name, image);
        return ResponseEntity.ok(updateClub);
    }

    @DeleteMapping("/{club_id}")
    public ResponseEntity<Void> deleteClub(@PathVariable("club_id") String clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.noContent().build();
    }
}
