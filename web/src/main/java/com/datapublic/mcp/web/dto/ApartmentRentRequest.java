package com.datapublic.mcp.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 아파트 전월세 실거래가 API 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentRentRequest {
    
    /**
     * 공공데이터 포털 서비스키 (필수)
     */
    private String serviceKey;
    
    /**
     * 지역코드 (필수)
     * 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * 예: 11680 (강남구)
     */
    private String LAWD_CD;
    
    /**
     * 계약월 (필수)
     * 실거래 자료의 계약년월(6자리)
     * 예: 202401
     */
    private String DEAL_YMD;
    
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
}

