package com.mozip.mozip.domain.user.service;

import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.user.entity.Position;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.user.entity.enums.PositionType;
import com.mozip.mozip.domain.user.exception.PositionException;
import com.mozip.mozip.domain.user.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public Position getPositionByUserAndClub(User user, Club club) {
        return positionRepository.findByUserIdAndClubId(user.getId(), club.getId())
                .orElseThrow(PositionException::new);
    }

    public boolean checkEvaluablePosition(Position position) {
        return positionRepository.existsByIdAndPositionNameIn(position.getId(), PositionType.evaluablePositions);
    }

    public boolean checkReadablePosition(Position position) {
        return positionRepository.existsByIdAndPositionNameIn(position.getId(), PositionType.readablePositions);
    }
}
