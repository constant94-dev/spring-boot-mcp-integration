package com.datapublic.mcp.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 아파트 전월세 실거래가 API 응답 아이템 DTO
 * 실제 API 응답 스펙에 맞게 수정
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentRentItem {
    
    /**
     * 아파트명
     */
    @JsonProperty("aptNm")
    private String aptNm;
    
    /**
     * 건축년도
     */
    @JsonProperty("buildYear")
    private String buildYear;
    
    /**
     * 계약기간
     */
    @JsonProperty("contractTerm")
    private String contractTerm;
    
    /**
     * 계약형태
     */
    @JsonProperty("contractType")
    private String contractType;
    
    /**
     * 계약일
     */
    @JsonProperty("dealDay")
    private String dealDay;
    
    /**
     * 계약월
     */
    @JsonProperty("dealMonth")
    private String dealMonth;
    
    /**
     * 계약년
     */
    @JsonProperty("dealYear")
    private String dealYear;
    
    /**
     * 보증금
     */
    @JsonProperty("deposit")
    private String deposit;
    
    /**
     * 전용면적
     */
    @JsonProperty("excluUseAr")
    private String excluUseAr;
    
    /**
     * 층
     */
    @JsonProperty("floor")
    private String floor;
    
    /**
     * 지번
     */
    @JsonProperty("jibun")
    private String jibun;
    
    /**
     * 월세
     */
    @JsonProperty("monthlyRent")
    private String monthlyRent;
    
    /**
     * 이전 보증금
     */
    @JsonProperty("preDeposit")
    private String preDeposit;
    
    /**
     * 이전 월세
     */
    @JsonProperty("preMonthlyRent")
    private String preMonthlyRent;
    
    /**
     * 시군구 코드
     */
    @JsonProperty("sggCd")
    private String sggCd;
    
    /**
     * 읍면동명
     */
    @JsonProperty("umdNm")
    private String umdNm;
    
    /**
     * 사용승인일
     */
    @JsonProperty("useRRRight")
    private String useRRRight;
    
    // 편의 메서드들
    public String getApartmentName() {
        return aptNm;
    }
    
    public String getLegalDong() {
        return umdNm;
    }
    
    public String getRentAmount() {
        return monthlyRent;
    }
    
    public String getDepositAmount() {
        return deposit;
    }
    
    public String getContractYearMonth() {
        if (dealYear != null && dealMonth != null) {
            return dealYear + String.format("%02d", Integer.parseInt(dealMonth));
        }
        return null;
    }
    
    public String getContractDay() {
        return dealDay;
    }
    
    public String getExclusiveArea() {
        return excluUseAr;
    }
    
    public String getFloor() {
        return floor;
    }
    
    public String getLotNumber() {
        return jibun;
    }
    
    public String getLawdCd() {
        return sggCd;
    }
}

