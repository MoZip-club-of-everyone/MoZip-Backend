package com.mozip.mozip.domain.club.service;

import com.mozip.mozip.domain.club.dto.ClubHomeResDto;
import com.mozip.mozip.domain.club.dto.ClubResponseDto;
import com.mozip.mozip.domain.club.dto.PositionResDto;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.repository.ClubRepository;
import com.mozip.mozip.domain.user.entity.Position;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.user.entity.enums.PositionType;
import com.mozip.mozip.domain.user.repository.PositionRepository;
import com.mozip.mozip.domain.user.service.UserService;
import com.mozip.mozip.global.service.S3Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final PositionRepository positionRepository;
    private final S3Service s3Service;
    private final UserService userService;

    public Club getClubById(String clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("Club이 없습니다 : " + clubId));
    }

    public Position getPositionByUserIdAndClubId(String userId, String clubId){
        return positionRepository.findByUserIdAndClubId(userId, clubId)
                .orElse(null);
    }

    public List<ClubHomeResDto> getClubsByUserId(String userId) {
        List<Club> clubs = positionRepository.findClubsByUserId(userId);
        return clubs.stream().map(this::clubHomeResDto).toList();
    }

    private ClubHomeResDto clubHomeResDto(Club club) {
        String masterName = club.getPosition().stream()
                .filter(position -> position.getPositionName() == PositionType.MASTER)
                .map(position -> position.getUser().getRealname())
                .findFirst()
                .orElse(null);

        int mozipCount = club.getMozip().size();

        return new ClubHomeResDto(
                club.getId(),
                club.getName(),
                club.getImage(),
                masterName,
                mozipCount
        );
    }

    @Transactional
    public List<PositionResDto> getPositionsByClubId(String clubId){
        List<Position> positions = positionRepository.findByClubId(clubId);

        List<String> order = List.of("마스터", "관리", "평가", "조회");
        Map<String, Integer> orderMap = IntStream.range(0, order.size())
                .boxed()
                .collect(Collectors.toMap(order::get, i -> i));

        return positions.stream()
                .filter(position -> position.getPositionName() != null)
                .map(this::positionResDto)
                .sorted(Comparator.comparingInt(p -> orderMap.getOrDefault(p.getPositionName(), Integer.MAX_VALUE))) // 정렬 기준 적용
                .toList();
    }

    private PositionResDto positionResDto(Position position){
        User user = position.getUser();
        String lastFourPhone = user.getPhone().substring(user.getPhone().length()-4);
        PositionType positionType = position.getPositionName();
        return new PositionResDto(
                user.getId(),
                user.getRealname() + "(" + lastFourPhone + ")",
                position.getPositionName().getValue()
        );
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

    @Transactional
    public void deleteUserInClub(String clubId, String userId){
        Position position = getPositionByUserIdAndClubId(userId, clubId);
        positionRepository.delete(position);
    }

    @Transactional
    public boolean inviteClub(String clubId, String email){
        User user = userService.getUserByEmail(email);
        if (getPositionByUserIdAndClubId(user.getId(), clubId) != null){
            return false;
        }
        Club club = getClubById(clubId);
        Position position = Position.builder()
                .positionName(PositionType.READ)
                .club(club)
                .user(user)
                .build();
        positionRepository.save(position);
        return true;
    }

    @Transactional
    public void updatePosition(String clubId, String userId, PositionType positionName){
        Position position = getPositionByUserIdAndClubId(userId, clubId);
        position.setPositionName(positionName);
        positionRepository.save(position);
    }

    public long countEvaluatorsByClub(Club club) {
        return positionRepository.countByClubAndPositionNameIn(club, PositionType.evaluablePositions);
    }
}
