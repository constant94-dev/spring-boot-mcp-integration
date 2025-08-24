package com.datapublic.mcp.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 공공데이터 포털 API 에러 코드 정의
 */
@Getter
@RequiredArgsConstructor
public enum PublicDataErrorCode {
    
    // 정상
    NORMAL("00", "NORMAL SERVICE.", "정상"),
    
    // 시스템 에러
    APPLICATION_ERROR("01", "APPLICATION ERROR", "애플리케이션 에러"),
    DB_ERROR("02", "DB ERROR", "데이터베이스 에러"),
    NO_DATA("03", "NO DATA", "데이터 없음"),
    HTTP_ERROR("04", "HTTP ERROR", "HTTP 에러"),
    SERVICETIMEOUT("05", "SERVICETIMEOUT", "서비스 타임아웃"),
    
    // 요청 파라미터 에러
    INVALID_REQUEST_PARAMETER("10", "INVALID REQUEST PARAMETER ERROR", "잘못된 요청 파라미터"),
    NO_MANDATORY_REQUEST_PARAMETERS("11", "NO_MANDATORY_REQUEST_PARAMETERS_ERROR", "필수 요청 파라미터 없음"),
    NO_OPENAPI_SERVICE("12", "NO_OPENAPI_SERVICE_ERROR", "해당 오픈 API 서비스가 없음"),
    
    // 인증/권한 에러
    SERVICE_ACCESS_DENIED("20", "SERVICE_ACCESS_DENIED_ERROR", "서비스 접근 거부"),
    LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS("22", "LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR", "서비스 요청 제한 초과"),
    SERVICE_KEY_IS_NOT_REGISTERED("30", "SERVICE_KEY_IS_NOT_REGISTERED_ERROR", "등록되지 않은 서비스키"),
    DEADLINE_HAS_EXPIRED("31", "DEADLINE_HAS_EXPIRED_ERROR", "기한 만료"),
    UNREGISTERED_IP("32", "UNREGISTERED_IP_ERROR", "등록되지 않은 IP"),
    UNSIGNED_CALL("33", "UNSIGNED_CALL_ERROR", "서명되지 않은 호출");
    
    private final String code;
    private final String message;
    private final String description;
    
    /**
     * 에러 코드로 PublicDataErrorCode 찾기
     */
    public static PublicDataErrorCode fromCode(String code) {
        for (PublicDataErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return APPLICATION_ERROR; // 기본값
    }
    
    /**
     * 정상 응답인지 확인
     */
    public boolean isSuccess() {
        return this == NORMAL;
    }
    
    /**
     * 에러인지 확인
     */
    public boolean isError() {
        return !isSuccess();
    }
}

