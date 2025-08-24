package com.datapublic.mcp.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 공공데이터 포털 API 공통 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicDataRequest {
    
    /**
     * 공공데이터 포털 서비스키
     */
    private String serviceKey;
    
    /**
     * 페이지 번호 (기본값: 1)
     */
    @Builder.Default
    private Integer pageNo = 1;
    
    /**
     * 한 페이지 결과 수 (기본값: 10)
     */
    @Builder.Default
    private Integer numOfRows = 10;
    
    /**
     * 응답 타입 (기본값: json)
     */
    @Builder.Default
    private String type = "json";
    
    /**
     * 시도명 (대기오염정보 API용)
     */
    private String sidoName;
    
    /**
     * 버전 (대기오염정보 API용)
     */
    private String ver;
}

