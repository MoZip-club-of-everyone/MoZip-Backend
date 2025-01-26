package com.mozip.mozip.domain.club.service;

import com.mozip.mozip.domain.club.dto.ClubHomeResDto;
import com.mozip.mozip.domain.club.dto.ClubResponseDto;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.repository.ClubRepository;
import com.mozip.mozip.domain.mozip.repository.MozipRepository;
import com.mozip.mozip.domain.user.entity.enums.PositionType;
import com.mozip.mozip.domain.user.repository.PositionRepository;
import com.mozip.mozip.global.service.S3Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final MozipRepository mozipRepository;
    private final PositionRepository positionRepository;
    private final S3Service s3Service;

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
    public ClubResponseDto createClub(String name, MultipartFile image) {
        if (clubRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 동아리입니다.");
        }
        try {
            String fileName = s3Service.createFileName(image);
            String imageURI = s3Service.upload(image, fileName);
            Club club = Club.builder()
                    .name(name)
                    .image(fileName)
                    .build();
            clubRepository.save(club);
            Club responseClub = Club.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .image(imageURI)
                    .build();
            return ClubResponseDto.fromEntity(responseClub);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 문제가 발생했습니다.");
        }
    }

    @Transactional
    public ClubResponseDto updateClub(String clubId, String name, MultipartFile image) {
        try {
            Club club = getClubById(clubId);
            club.setName(name);
            log.info(club.getImage());
            if (club.getImage() != null) {
                s3Service.delete(club.getImage());
            }
            String fileName = s3Service.createFileName(image);
            String imageURI = s3Service.upload(image, fileName);
            club.setImage(fileName);
            clubRepository.save(club);
            Club responseClub = Club.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .image(imageURI)
                    .build();
            return ClubResponseDto.fromEntity(responseClub);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 문제가 발생했습니다.");
        }
    }

    @Transactional
    public void deleteClub(String clubId) {
        Club club = getClubById(clubId);
        clubRepository.delete(club);
    }
}
