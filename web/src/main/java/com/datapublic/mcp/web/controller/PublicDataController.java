package com.datapublic.mcp.web.controller;

import com.datapublic.mcp.web.dto.ApartmentRentItem;
import com.datapublic.mcp.web.dto.SeoulDistrictCode;
import com.datapublic.mcp.web.exception.PublicDataApiException;
import com.datapublic.mcp.web.service.ApartmentRentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 공공데이터 포털 API 컨트롤러
 */
@RestController
@RequestMapping("/api/public-data")
@RequiredArgsConstructor
@Slf4j
public class PublicDataController {
    
    private final ApartmentRentService apartmentRentService;
    
    /**
     * 서울특별시 아파트 전월세 실거래가 조회 (지역코드로)
     * 
     * @param districtCode 지역코드 (5자리)
     * @param dealYearMonth 계약년월 (6자리, 예: 202401)
     * @param pageNo 페이지 번호 (기본값: 1)
     * @param numOfRows 한 페이지 결과 수 (기본값: 10)
     * @return 아파트 전월세 실거래가 목록
     */
    @GetMapping("/apartment-rent")
    public ResponseEntity<Map<String, Object>> getApartmentRentData(
            @RequestParam String districtCode,
            @RequestParam String dealYearMonth,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer numOfRows) {
        
        log.info("🏠 아파트 전월세 실거래가 조회 요청 - 지역코드: {}, 계약년월: {}", districtCode, dealYearMonth);
        
        try {
            List<ApartmentRentItem> items = apartmentRentService.getApartmentRentData(
                    districtCode, dealYearMonth, pageNo, numOfRows);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", items);
            response.put("totalCount", items.size());
            response.put("pageNo", pageNo);
            response.put("numOfRows", numOfRows);
            
            return ResponseEntity.ok(response);
            
        } catch (PublicDataApiException e) {
            log.error("❌ 아파트 전월세 실거래가 조회 실패", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", e.getErrorCode());
            errorResponse.put("errorMessage", e.getErrorMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * 서울특별시 아파트 전월세 실거래가 조회 (구명으로)
     * 
     * @param districtName 구명 (예: "강남구")
     * @param dealYearMonth 계약년월 (6자리, 예: 202401)
     * @param pageNo 페이지 번호 (기본값: 1)
     * @param numOfRows 한 페이지 결과 수 (기본값: 10)
     * @return 아파트 전월세 실거래가 목록
     */
    @GetMapping("/apartment-rent/district")
    public ResponseEntity<Map<String, Object>> getApartmentRentDataByDistrictName(
            @RequestParam String districtName,
            @RequestParam String dealYearMonth,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer numOfRows) {
        
        log.info("🏠 아파트 전월세 실거래가 조회 요청 - 구명: {}, 계약년월: {}", districtName, dealYearMonth);
        
        try {
            List<ApartmentRentItem> items = apartmentRentService.getApartmentRentDataByDistrictName(
                    districtName, dealYearMonth, pageNo, numOfRows);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", items);
            response.put("totalCount", items.size());
            response.put("pageNo", pageNo);
            response.put("numOfRows", numOfRows);
            
            return ResponseEntity.ok(response);
            
        } catch (PublicDataApiException e) {
            log.error("❌ 아파트 전월세 실거래가 조회 실패", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", e.getErrorCode());
            errorResponse.put("errorMessage", e.getErrorMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * 서울특별시 구 목록 조회
     * 
     * @return 서울특별시 구 목록
     */
    @GetMapping("/districts")
    public ResponseEntity<Map<String, Object>> getDistricts() {
        log.info("🗺️ 서울특별시 구 목록 조회 요청");
        
        try {
            List<SeoulDistrictCode.District> districts = apartmentRentService.getAvailableDistricts();
            Map<String, String> codeMapping = apartmentRentService.getDistrictCodeMapping();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("districts", districts);
            response.put("codeMapping", codeMapping);
            response.put("totalCount", districts.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("❌ 서울특별시 구 목록 조회 실패", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorMessage", "구 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 공공데이터 포털 API 상태 확인
     * 
     * @return API 상태 정보
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getApiStatus() {
        log.info("🔍 공공데이터 포털 API 상태 확인 요청");
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("service", "공공데이터 포털 API");
        response.put("status", "ACTIVE");
        response.put("availableApis", List.of("아파트 전월세 실거래가"));
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
}
