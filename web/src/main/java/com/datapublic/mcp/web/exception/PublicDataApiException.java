package com.datapublic.mcp.web.exception;

/**
 * 공공데이터 포털 API 호출 시 발생하는 예외
 */
public class PublicDataApiException extends RuntimeException {
    
    private final String errorCode;
    private final String errorMessage;
    
    public PublicDataApiException(String errorCode, String errorMessage) {
        super(String.format("공공데이터 API 오류 [%s]: %s", errorCode, errorMessage));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public PublicDataApiException(String errorCode, String errorMessage, Throwable cause) {
        super(String.format("공공데이터 API 오류 [%s]: %s", errorCode, errorMessage), cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}

