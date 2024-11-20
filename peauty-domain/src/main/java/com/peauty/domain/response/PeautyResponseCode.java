package com.peauty.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PeautyResponseCode {
    OK("0000", "OK", "정상 처리되었습니다."),
    // 토큰 관련 (1100 ~ 1150)
    NOT_EXIST_BEARER_SUFFIX("1100", "Bearer Suffix Missing", "Bearer 접두사가 포함되지 않았습니다."),
    WRONG_JWT_TOKEN("1101", "Invalid JWT Token", "잘못된 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN("1102", "Expired JWT Token", "만료된 JWT 토큰입니다."),
    EMPTY_AUTH_JWT("1103", "Empty Auth JWT", "인증 정보가 비어있는 JWT 토큰입니다."),
    EMPTY_USER("1104", "Empty User Info", "비어있는 유저 정보로 JWT 토큰을 생성할 수 없습니다."),
    INVALID_KEY("1105", "Invalid Key", "잘못된 키입니다."),
    EMPTY_REFRESH("1106", "Refresh Token Missing", "리프레시 토큰이 존재하지 않습니다."),
    BLACK_LIST_TOKEN("1107", "Blacklisted Token", "블랙리스트에 등록된 토큰입니다."),
    EMPTY_ACCESS("1108", "Access Token Missing", "액세스 토큰이 존재하지 않습니다."),
    // 공통 유저 관련 (1200 ~ 1250)
    WRONG_PROVIDER("1200", "Invalid Provider", "잘못된 인증 제공자입니다."),
    NOT_EXIST_USER("1201", "User Not Found", "존재하지 않는 유저입니다."),
    ALREADY_EXIST_USER("1202", "User Already Exists", "이미 가입한 유저입니다."),
    INVALID_USER_TYPE("1203", "Invalid User Type", "디자이너 혹은 고객 타입의 유저만 가능합니다."),
    ALREADY_EXIST_NICKNAME("1204", "Nickname Already Exits", "이미 존재하는 닉네임입니다."),
    ALREADY_EXIST_PHONE_NUM("1205", "Phone Num Already Exits", "이미 존재하는 휴대폰 번호입니다."),
    // 외부 모듈 관련
    APPLE_AUTH_CLIENT_ERROR("8100", "Apple auth client error", "일시적인 에러가 발생하였습니다. 잠시 후 다시 시도해주세요."),
    KAKAO_AUTH_CLIENT_ERROR("8200", "Kakao auth client error", "일시적인 에러가 발생하였습니다. 잠시 후 다시 시도해주세요."),
    GOOGLE_AUTH_CLIENT_ERROR("8300", "Google auth client error", "일시적인 에러가 발생하였습니다. 잠시 후 다시 시도해주세요."),
    // 클라이언트 에러
    BAD_REQUEST("9400", "Bad Request", "잘못된 요청입니다."),
    WRONG_PARAMETER("9401", "Invalid Parameter", "잘못된 파라미터입니다."),
    METHOD_NOT_ALLOWED("9402", "Method Not Allowed", "허용되지 않은 메서드입니다."),
    UNAUTHORIZED("9403", "Unauthorized", "권한이 없습니다."),
    REQUEST_TIMEOUT("9900", "Request Timeout", "일시적인 에러가 발생하였습니다. 잠시 후 다시 시도해주세요."),
    NOT_YET_IMPLEMENTED("9901", "Not Yet Implemented", "아직 완성되지 않은 기능입니다."),
    INTERNAL_SERVER_ERROR("9999", "Internal Server Error", "내부 서버 에러가 발생하였습니다.");

    private final String code;
    private final String message;
    private final String serviceMessage;

    public static PeautyResponseCode lookupResponseCode(String code) {
        for (PeautyResponseCode peautyResponseCode : values()) {
            if (peautyResponseCode.getCode().equals(code)) {
                return peautyResponseCode;
            }
        }
        return null;
    }
}