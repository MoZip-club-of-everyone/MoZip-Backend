package com.mozip.mozip.domain.club.controller;

import com.mozip.mozip.domain.club.dto.*;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.service.ClubService;
import com.mozip.mozip.domain.user.entity.Position;
import com.mozip.mozip.domain.user.entity.enums.PositionType;
import com.mozip.mozip.domain.user.service.UserService;
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
    private final UserService userService;

    @GetMapping("/club/{club_id}")
    public ResponseEntity<Club> getClubById(@PathVariable("Club_id") String clubId) {
        return ResponseEntity.ok(clubService.getClubById(clubId));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<ClubHomeResDto>> getClubsByUserId(@PathVariable("user_id") String userId) {
        return ResponseEntity.ok(clubService.getClubsByUserId(userId));
    }

    @GetMapping("{club_id}/manage")
    public ResponseEntity<List<PositionResDto>> getPositionsByCludId(@PathVariable("club_id") String clubId){
        return ResponseEntity.ok(clubService.getPositionsByClubId(clubId));
    }

    @PostMapping
    public ResponseEntity<?> createClub(
            @RequestParam("userId") String userId,
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image) {
        try {
            ClubResponseDto createdClub = clubService.createClub(name, image);
            clubService.inviteClub(createdClub.getClubId(), userService.getUserById(userId).getEmail());
            clubService.updatePosition(createdClub.getClubId(), userId, PositionType.MASTER);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClub);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{club_id}/invite")
    public ResponseEntity<String> inviteClub(
            @PathVariable("club_id") String clubId,
            @RequestBody ClubinviteReqDto clubinviteReqDto){
        Position position = clubService.inviteClub(clubId, clubinviteReqDto.getEmail());
        return ResponseEntity.ok("성공적으로 초대되었습니다.");
    }

    @PutMapping("/{club_id}/{user_id}/position")
    public ResponseEntity<String> updatePosition(
            @PathVariable("club_id") String clubId,
            @PathVariable("user_id") String userId,
            @RequestBody PositionReqDto positionReqDto) {
        clubService.updatePosition(clubId, userId, positionReqDto.getPositionName());
        return ResponseEntity.ok("권한이 수정되었습니다.");
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

    @DeleteMapping("{club_id}/{user_id}/role")
    public ResponseEntity<String> deleteUserInClub(
            @PathVariable("club_id") String clubId,
            @PathVariable("user_id") String userId) {
        clubService.deleteUserInClub(clubId, userId);
        return ResponseEntity.ok("사용자 추방에 성공하였습니다.");
    }
}
