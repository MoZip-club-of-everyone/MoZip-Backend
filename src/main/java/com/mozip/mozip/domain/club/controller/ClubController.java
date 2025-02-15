package com.mozip.mozip.domain.club.controller;

import com.mozip.mozip.domain.club.dto.*;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.service.ClubService;
import com.mozip.mozip.domain.user.entity.Position;
import com.mozip.mozip.domain.user.entity.enums.PositionType;
import com.mozip.mozip.domain.user.service.UserService;
import com.mozip.mozip.global.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/{club_id}/position")
    public ResponseEntity<String> inviteClub(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("club_id") String clubId,
            @RequestBody ClubinviteReqDto clubinviteReqDto){
        Position position = clubService.getPositionByUserIdAndClubId(customUserDetails.getId(), clubId);
        if (position != null && position.getPositionName().isMaster() || position.getPositionName().isManager()) {
            if (clubService.inviteClub(clubId, clubinviteReqDto.getEmail())){
                return ResponseEntity.ok("성공적으로 초대되었습니다.");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 초대된 운영진입니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("권한이 없습니다.");
        }
    }

    @PutMapping("/{club_id}/position")
    public ResponseEntity<String> updatePosition(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("club_id") String clubId,
            @RequestBody PositionReqDto positionReqDto) {
        Position position = clubService.getPositionByUserIdAndClubId(customUserDetails.getId(), clubId);
        if (position != null && position.getPositionName().isMaster() || position.getPositionName().isManager()) {
            clubService.updatePosition(clubId, positionReqDto.getRealname(), positionReqDto.getPositionName());
            return ResponseEntity.ok("권한이 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("권한이 없습니다.");
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
    public ResponseEntity<String> deleteClub(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("club_id") String clubId) {
        Position position = clubService.getPositionByUserIdAndClubId(customUserDetails.getId(), clubId);
        if (position != null && position.getPositionName().isMaster()) {
            clubService.deleteClub(clubId);
            return ResponseEntity.ok("동아리가 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("권한이 없습니다.");
        }
    }

    @DeleteMapping("/{club_id}/position")
    public ResponseEntity<String> deleteUserInClub(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UserDeleteInClubReqDto userDeleteInClubReqDto,
            @PathVariable("club_id") String clubId) {
        Position position = clubService.getPositionByUserIdAndClubId(customUserDetails.getId(), clubId);
        if (position != null && position.getPositionName().isMaster() || position.getPositionName().isManager()) {
            clubService.deleteUserInClub(clubId, userDeleteInClubReqDto.getRealname());
            return ResponseEntity.ok("사용자 추방에 성공하였습니다.");
        } else {
            return ResponseEntity.ok("권한이 없습니다.");
        }
    }
}
