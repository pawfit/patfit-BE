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
    NOT_EXISTS_BREED("1206", "Breed Not Found", "존재하지 않는 견종입니다."),
    NOT_EXISTS_DISEASE("1207", "Disease Not Found", "존재하지 않는 병명입니다."),
    WRONG_SEX("1208", "Invalid Puppy's Sex", "잘못된 애완견의 성별입니다."),
    WRONG_SIZE("1209", "Invalid Puppy's Size", "잘못된 애완견의 크기입니다."),
    NOT_FOUND_PUPPY("1210", "Puppy Not Found", "찾을 수 없는 반려견입니다."),
    INVALID_RATING("1211", "Invalid Rating", "잘못된 리뷰 평점입니다."),
    INVALID_GENERAL_CONTENT("1212", "Invalid GENERAL CONTENT", "잘못된 리뷰 선택 컨텐츠입니다."),


    // 비딩 관련 (1300 ~ 1350)
    WRONG_BIDDING_PROCESS_STEP_DESCRIPTION("1300", "Wrong Bidding Process Step Description", "잘못된 입찰 프로세스입니다."),
    WRONG_BIDDING_PROCESS_STEP("1301", "Wrong Bidding Process Step", "잘못된 입찰 프로세스 단계입니다."),
    INVALID_BACKWARD_BIDDING_STEP("1302", "Wrong Bidding Process Step", "입찰 프로세스 단계를 뒤로 돌릴 순 없습니다."),

    WRONG_BIDDING_PROCESS_EXCEPTION_STATUS("1303", "Wrong Bidding Process Exception Status", "잘못된 입찰 프로세스 예외 상태입니다."),
    WRONG_BIDDING_THREAD_STEP_DESCRIPTION("1304", "Wrong Bidding Process Thread Step Description", "잘못된 입찰 스레드입니다."),
    WRONG_BIDDING_THREAD_STEP("1305", "Wrong Bidding Process Thread Step", "잘못된 입찰 스레드 단계입니다."),
    WRONG_BIDDING_THREAD_EXCEPTION_STATUS("1306", "Wrong Bidding Thread Exception Status", "잘못된 입찰 스레드 예외 상태입니다."),
    NOT_FOUND_BIDDING_THREAD_IN_PROCESS("1307", "Bidding Thread Not Found", "찾을 수 없는 입찰 스레드입니다."),
    ALREADY_CANCELED_BIDDING_PROCESS("1308", "Bidding Process Already Canceled", "이미 취소된 입찰 프로세스입니다."),
    ALREADY_COMPLETED_BIDDING_PROCESS("1309", "Bidding Process Already Completed", "이미 완료된 입찰 프로세스입니다."),
    THREAD_ALREADY_IN_PROCESS("1310", "Thread Already In Process", "이미 해당 프로세스에 속한 스레드입니다."),
    ALREADY_COMPLETED_BIDDING_THREAD("1311", "Bidding Thread Already Completed", "이미 완료된 입찰 스레드입니다."),
    ALREADY_CANCELED_BIDDING_THREAD("1312", "Bidding Thread Already Canceled", "이미 취소된 입찰 스레드입니다."),
    CANNOT_CANCEL_COMPLETED_THREAD("1313", "Cannot Cancel Completed Thread", "완료된 입찰 스레드는 취소할 수 없습니다."),
    CANNOT_PROGRESS_CANCELED_THREAD_STEP("1314", "Cannot Progress Canceled Thread Step", "취소된 스레드의 단계는 변경할 수 없습니다."),
    CANNOT_PROGRESS_WAITING_THREAD_STEP("1315", "Cannot Progress Waiting Thread Step", "대기 중인 스레드의 단계는 변경할 수 없습니다."),
    INVALID_STEP_PROGRESSING("1316", "Invalid Step Progressing", "해당 단계로 올릴 수 있는 단계가 아닙니다."),

    // AWS 관련 (7000 ~ 8000)
    IMAGE_UPLOAD_FAIl("7001", "Fail To Upload Image To S3", "S3 에 이미지를 업로드하는 것을 실패했습니다."),

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