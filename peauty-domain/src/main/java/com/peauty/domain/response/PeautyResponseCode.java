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
    ALREADY_EXIST_PHONE_NUMBER("1205", "Phone Num Already Exits", "이미 존재하는 휴대폰 번호입니다."),
    NOT_EXISTS_BREED("1206", "Breed Not Found", "존재하지 않는 견종입니다."),
    NOT_EXISTS_DISEASE("1207", "Disease Not Found", "존재하지 않는 병명입니다."),
    WRONG_SEX("1208", "Invalid Puppy's Sex", "잘못된 애완견의 성별입니다."),
    WRONG_SIZE("1209", "Invalid Puppy's Size", "잘못된 애완견의 크기입니다."),
    NOT_FOUND_PUPPY("1210", "Puppy Not Found", "찾을 수 없는 반려견입니다."),
    NOT_EXIST_WORKSPACE("1211", "Workspace Not Found", "가게가 존재하지 않습니다."),
    INVALID_RATING("1211", "Invalid Rating", "잘못된 리뷰 평점입니다."),
    INVALID_GENERAL_CONTENT("1212", "Invalid GENERAL CONTENT", "잘못된 리뷰 선택 컨텐츠입니다."),
    INVALID_PAYMENT_OPTION("1213", "Invalid Payment Options", "잘못된 결제 방식입니다"),
    ALREADY_EXIST_WORKSPACE("1214", "Already Exist Workspace", "해당 유저는 이미 가게가 존재합니다."),
    INVALID_ADDRESS_FORMAT("1215", "Invalid Address Format", "잘못된 주소 형식입니다."),
    NOT_EXIST_DESIGNER("1216", "Designer Not Found", "찾을 수 없는 디자이너입니다."),
    INVALID_LICENSE_VERIFICATION_OPTION("1217", "Invalid License Verification Option", "잘못된 자격증 검증 옵션입니다."),
    INVALID_SCISSORS_RANK_OPTION("1218", "Invalid Scissors Rank Option", "잘못된 시저 랭크 옵션입니다."),
    INVALID_COLOR_OPTION("1219", "Invalid Color Option", "잘못된 컬러 옵션입니다."),
    ALREADY_FULL_BADGE("1220", "Already Full Representative Badge", "이미 대표 뱃지가 3개입니다."),
    NOT_EXIST_BADGE("1221", "Not Exist Badge", "해당 뱃지를 찾을 수 없습니다."),
    NOT_OWNER_OF_PUPPY("1222", "Not Owner Of The Puppy", "해당 유저가 소유하고 있는 강아지가 아닙니다."),
    CANNOT_REVIEW_INCOMPLETED_THREAD("1223", "Can Not Review Incompleted Thread", "아직 미용이 완료되지 않아 리뷰를 작성할 수 없습니다."),
    INVALID_REVIEW_THREAD_MISMATCH("1224", "Invalid Review Thread Mismatch", "해당 미용과 리뷰가 일치하지 않아 수정할 수 없습니다."),
    INVALID_REVIEW_USER_OR_PUPPY("1225", "Invalid Review User Or Puppy", "본인이 아니면 해당 리뷰를 수정할 수 없습니다."),
    NOT_FOUND_REVIEW("1226", "Not Found Review", "해당 리뷰를 찾을 수 없습니다."),
    INVALID_REVIEW_UPDATE("1227", "No Review, Invalid Review Update", "현재 리뷰가 존재하지 않아 수정할 수 없습니다."),
    WRONG_BIRTHDATE("1228", "Write In Your Puppy Birthday", "생년월일을 입력하세요."),
    INVALID_BIRTHDATE("1229", "Invalid Your Puppy Birthday", "현재 날짜보다 이후를 선택했습니다."),

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
    ONLY_BELONGING_PROCESS_CAN_BE_OBSERVER("1317", "Invalid Process Observer", "속한 프로세스가 아닌 다른 프로세스의 상태는 변경할 수 없습니다."),
    PROCESS_OBSERVER_NOT_REGISTERED("1318", "Observer Not Registered", "스레드의 상태가 변경되면 속한 프로세스의 상태도 변경될 수 있어야합니다."),
    PROCESS_NOT_REGISTERED("1319", "Process Not Registered", "스레드는 등록되지 않은 프로세스에 속할 수 없습니다."),
    NOT_INITIALIZED_PROCESS("1320", "Not Initialized Process", "초기화되지 않은 프로세스입니다."),
    NOT_FOUND_BIDDING_PROCESS("1321", "Not Found Bidding Process", "해당 입찰 프로세스를 찾을 수 없습니다."),

    INVALID_GROOMING_TYPE("1400", "Invalid Grooming Type", "올바르지 않은 미용 타입입니다."),
    INVALID_FACE_TYPE("1401", "Invalid Face Type", "올바르지 않은 얼굴 미용 타입입니다."),
    INVALID_BODY_TYPE("1402", "Invalid Body Type", "올바르지 않은 몸 미용 타입입니다."),
    NOT_FOUND_ESTIMATE_PROPOSAL("1403", "Not Found Estimate Proposal", "견적 제안서를 찾을 수 없습니다."),
    NOT_FOUND_ESTIMATE("1404", "Not Found Estimate", "견적서를 찾을 수 없습니다."),
    PROCESS_ALREADY_IN_PROGRESS("1405", "Bidding Process Already In Progress", "이미 진행 중이거나 예약된 입찰 프로세스가 있습니다."),

    // PAYMENT - IAMPORT
    IAMPORT_ERROR("1500", "Iamport Exception", "포트원 예외가 발생하였습니다. 잠시 후 다시 시도해주세요"),
    PAYMENT_AMOUNT_NOT_EQUALS("1501", "Payment Not Equals", "실제 결제할 금액과 다릅니다. 결제 위변조 가능성이 있습니다."),
    NOT_FOUND_PAYMENT("1502", "Payment Not Found", "해당 결제 건을 찾을 수 없습니다."),
    NOT_FOUND_ACTUAL_PRICE("1503", "Actual Price Not Found", "결제할 금액이 없습니다."),
    NOT_FOUND_ORDER("1504", "Order Not Found", "주문이 없습니다."),

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