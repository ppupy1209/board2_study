package study.board2.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "Member Not Found"),
    TAG_NOT_FOUND(404,"Tag Not Found"),


    ALREADY_MEMBER_NAME_EXISTS(409,"Already Member_Name Exists");
    @Getter
    int status;

    @Getter
    String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
