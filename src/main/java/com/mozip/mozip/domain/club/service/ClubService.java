package com.mozip.mozip.domain.club.service;

import com.mozip.mozip.domain.club.dto.ClubHomeResDto;
import com.mozip.mozip.domain.club.dto.ClubResponseDto;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.repository.ClubRepository;
import com.mozip.mozip.domain.club.repository.MozipRepository;
import com.mozip.mozip.domain.user.entity.enums.PositionType;
import com.mozip.mozip.domain.user.repository.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final MozipRepository mozipRepository;
    private final PositionRepository positionRepository;

    public List<ClubHomeResDto> getClubsByUserId(String userId) {
        List<Club> clubs = positionRepository.findClubsByUserId(userId);
        log.info(clubs.toString());
        return clubs.stream().map(this::clubHomeResDto).toList();
    }

    private ClubHomeResDto clubHomeResDto(Club club) {
        String leaderName = club.getPosition().stream()
                .filter(position -> position.getPositionName() == PositionType.LEADER)
                .map(position -> position.getUser().getRealname())
                .findFirst()
                .orElse(null);

        int mozipCount = club.getMozip().size();

        return new ClubHomeResDto(
                club.getId(),
                club.getName(),
                club.getImage(),
                leaderName,
                mozipCount
        );
    }


    public Club getClubById(String clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("Club이 없습니다 : " + clubId));
    }

    @Transactional
    public Club createClub(String name, String image) {
        Club club = Club.builder()
                .name(name)
                .image(image)
                .build();
        return clubRepository.save(club);
    }

    @Transactional
    public ClubResponseDto updateClub(String clubId, String name, String image) {
        Club club = getClubById(clubId);
        club.setName(name);
        club.setImage(image);
        Club savedClub = clubRepository.save(club);
        return ClubResponseDto.fromEntity(savedClub);
    }

    @Transactional
    public void deleteClub(String clubId) {
        Club club = getClubById(clubId);
        clubRepository.delete(club);
    }
}
