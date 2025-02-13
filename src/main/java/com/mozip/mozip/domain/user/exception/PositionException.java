package com.mozip.mozip.domain.user.exception;

import com.mozip.mozip.domain.user.entity.Position;

public class PositionException extends RuntimeException {
    public PositionException() {
      super("Position을 찾을 수 없습니다.");
    }

    public PositionException(Position position, String message) {
      super("Id: " + position.getId() + " - " + message);
    }

    public static PositionException notEvaluable(Position position) {
      return new PositionException(position, "평가 권한이 없는 Position입니다.");
    }

  public static PositionException notReadable(Position position) {
    return new PositionException(position, "조회 권한이 없는 Position입니다.");
  }
}
