package com.datapublic.mcp.web.service;

import com.datapublic.mcp.web.dto.ApartmentRentItem;
import com.datapublic.mcp.web.dto.ApartmentRentRequest;
import com.datapublic.mcp.web.dto.PublicDataApiResponse;
import com.datapublic.mcp.web.dto.SeoulDistrictCode;
import com.datapublic.mcp.web.exception.PublicDataApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 아파트 전월세 실거래가 API 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentRentService {
    
    private final PublicDataApiClient apiClient;
    
    /**
     * 아파트 전월세 실거래가 API 엔드포인트
     * 공공데이터 포털 정확한 스펙에 맞게 수정
     */
    private static final String APARTMENT_RENT_ENDPOINT = "/1613000/RTMSDataSvcAptRent/getRTMSDataSvcAptRent";
    
    /**
     * 서울특별시 아파트 전월세 실거래가 조회
     * 
     * @param districtCode 지역코드 (5자리)
     * @param dealYearMonth 계약년월 (6자리, 예: 202401)
     * @param pageNo 페이지 번호
     * @param numOfRows 한 페이지 결과 수
     * @return 아파트 전월세 실거래가 목록
     */
    public List<ApartmentRentItem> getApartmentRentData(String districtCode, String dealYearMonth, 
                                                       Integer pageNo, Integer numOfRows) {
        log.info("🏠 아파트 전월세 실거래가 조회 - 지역코드: {}, 계약년월: {}", districtCode, dealYearMonth);
        
        // 지역코드 유효성 검증
        validateDistrictCode(districtCode);
        
        // 계약년월 유효성 검증
        validateDealYearMonth(dealYearMonth);
        
        // 요청 파라미터 구성
        Map<String, String> params = buildRequestParams(districtCode, dealYearMonth, pageNo, numOfRows);
        
        // API 호출
        PublicDataApiResponse<ApartmentRentItem> response = apiClient.callApi(
                APARTMENT_RENT_ENDPOINT, 
                params, 
                ApartmentRentItem.class
        );
        
        // 응답 데이터 추출
        List<ApartmentRentItem> items = extractItems(response);
        
        log.info("✅ 아파트 전월세 실거래가 조회 완료 - 총 {}건", items.size());
        
        return items;
    }
    
    /**
     * 서울특별시 구 이름으로 아파트 전월세 실거래가 조회
     * 
     * @param districtName 구 이름 (예: "강남구")
     * @param dealYearMonth 계약년월 (6자리, 예: 202401)
     * @param pageNo 페이지 번호
     * @param numOfRows 한 페이지 결과 수
     * @return 아파트 전월세 실거래가 목록
     */
    public List<ApartmentRentItem> getApartmentRentDataByDistrictName(String districtName, String dealYearMonth,
                                                                     Integer pageNo, Integer numOfRows) {
        log.info("🏠 아파트 전월세 실거래가 조회 - 구명: {}, 계약년월: {}", districtName, dealYearMonth);
        
        // 구 이름으로 지역코드 찾기
        SeoulDistrictCode.District district = SeoulDistrictCode.District.fromName(districtName);
        if (district == null) {
            throw new PublicDataApiException("INVALID_DISTRICT", "유효하지 않은 구명: " + districtName);
        }
        
        return getApartmentRentData(district.getCode(), dealYearMonth, pageNo, numOfRows);
    }
    
    /**
     * 지역코드 유효성 검증
     */
    private void validateDistrictCode(String districtCode) {
        if (districtCode == null || districtCode.trim().isEmpty()) {
            throw new PublicDataApiException("INVALID_DISTRICT_CODE", "지역코드가 비어있습니다.");
        }
        
        if (!SeoulDistrictCode.isValidCode(districtCode)) {
            throw new PublicDataApiException("INVALID_DISTRICT_CODE", 
                    "유효하지 않은 지역코드: " + districtCode);
        }
    }
    
    /**
     * 계약년월 유효성 검증
     */
    private void validateDealYearMonth(String dealYearMonth) {
        if (dealYearMonth == null || dealYearMonth.trim().isEmpty()) {
            throw new PublicDataApiException("INVALID_DEAL_YEAR_MONTH", "계약년월이 비어있습니다.");
        }
        
        if (dealYearMonth.length() != 6) {
            throw new PublicDataApiException("INVALID_DEAL_YEAR_MONTH", 
                    "계약년월은 6자리여야 합니다: " + dealYearMonth);
        }
        
        try {
            Integer.parseInt(dealYearMonth);
        } catch (NumberFormatException e) {
            throw new PublicDataApiException("INVALID_DEAL_YEAR_MONTH", 
                    "계약년월은 숫자여야 합니다: " + dealYearMonth);
        }
    }
    
    /**
     * 요청 파라미터 구성
     * 공공데이터 포털 정확한 스펙에 맞게 수정
     * 
     * 요청 파라미터:
     * - serviceKey: 공공데이터포털에서 발급받은 인증키
     * - LAWD_CD: 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
     * - DEAL_YMD: 실거래 자료의 계약년월(6자리)
     * - pageNo: 페이지번호
     * - numOfRows: 한 페이지 결과 수
     */
    private Map<String, String> buildRequestParams(String districtCode, String dealYearMonth, 
                                                  Integer pageNo, Integer numOfRows) {
        Map<String, String> params = new HashMap<>();
        
        // 필수 파라미터
        params.put("LAWD_CD", districtCode);
        params.put("DEAL_YMD", dealYearMonth);
        
        // 선택 파라미터
        if (pageNo != null) {
            params.put("pageNo", String.valueOf(pageNo));
        }
        
        if (numOfRows != null) {
            params.put("numOfRows", String.valueOf(numOfRows));
        }
        
        return params;
    }
    
    /**
     * 응답에서 아이템 목록 추출
     */
    private List<ApartmentRentItem> extractItems(PublicDataApiResponse<ApartmentRentItem> response) {
        if (response == null || response.getResponse() == null || 
            response.getResponse().getBody() == null || 
            response.getResponse().getBody().getItems() == null) {
            return List.of();
        }
        
        return response.getResponse().getBody().getItems().getItem();
    }
    
    /**
     * 사용 가능한 서울특별시 구 목록 조회
     */
    public List<SeoulDistrictCode.District> getAvailableDistricts() {
        return SeoulDistrictCode.getAllDistricts();
    }
    
    /**
     * 지역코드-구명 매핑 조회
     */
    public Map<String, String> getDistrictCodeMapping() {
        return SeoulDistrictCode.getCodeNameMapping();
    }
}
