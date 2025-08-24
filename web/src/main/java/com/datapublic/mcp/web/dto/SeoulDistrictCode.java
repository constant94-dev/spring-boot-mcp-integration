package com.datapublic.mcp.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 서울특별시 지역코드 정보
 * 법정동코드 10자리 중 앞 5자리
 */
public class SeoulDistrictCode {
    
    /**
     * 서울특별시 구별 지역코드
     */
    @Getter
    @RequiredArgsConstructor
    public enum District {
        JONGNO("11110", "종로구"),
        JUNG("11140", "중구"),
        YONGSAN("11170", "용산구"),
        SEONGDONG("11200", "성동구"),
        GWANGJIN("11215", "광진구"),
        DONGDAEMUN("11230", "동대문구"),
        JUNGNANG("11260", "중랑구"),
        SEONGBUK("11290", "성북구"),
        GANGBUK("11305", "강북구"),
        DOBONG("11320", "도봉구"),
        NOWON("11350", "노원구"),
        EUNPYEONG("11380", "은평구"),
        SEODAEMUN("11410", "서대문구"),
        MAPO("11440", "마포구"),
        YANGCHEON("11470", "양천구"),
        GANGSEO("11500", "강서구"),
        GURO("11530", "구로구"),
        GEUMCHEON("11545", "금천구"),
        YEONGDEUNGPO("11560", "영등포구"),
        DONGJAK("11590", "동작구"),
        GWANAK("11620", "관악구"),
        SEOCHO("11650", "서초구"),
        GANGNAM("11680", "강남구"),
        SONGPA("11710", "송파구"),
        GANGDONG("11740", "강동구");
        
        private final String code;
        private final String name;
        
        /**
         * 코드로 District 찾기
         */
        public static District fromCode(String code) {
            return Arrays.stream(values())
                    .filter(district -> district.getCode().equals(code))
                    .findFirst()
                    .orElse(null);
        }
        
        /**
         * 이름으로 District 찾기
         */
        public static District fromName(String name) {
            return Arrays.stream(values())
                    .filter(district -> district.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
    }
    
    /**
     * 모든 서울특별시 구 목록 반환
     */
    public static List<District> getAllDistricts() {
        return Arrays.asList(District.values());
    }
    
    /**
     * 모든 서울특별시 구 코드 목록 반환
     */
    public static List<String> getAllCodes() {
        return Arrays.stream(District.values())
                .map(District::getCode)
                .collect(Collectors.toList());
    }
    
    /**
     * 모든 서울특별시 구 이름 목록 반환
     */
    public static List<String> getAllNames() {
        return Arrays.stream(District.values())
                .map(District::getName)
                .collect(Collectors.toList());
    }
    
    /**
     * 코드-이름 매핑 반환
     */
    public static Map<String, String> getCodeNameMapping() {
        return Arrays.stream(District.values())
                .collect(Collectors.toMap(
                        District::getCode,
                        District::getName
                ));
    }
    
    /**
     * 이름-코드 매핑 반환
     */
    public static Map<String, String> getNameCodeMapping() {
        return Arrays.stream(District.values())
                .collect(Collectors.toMap(
                        District::getName,
                        District::getCode
                ));
    }
    
    /**
     * 유효한 서울특별시 구 코드인지 확인
     */
    public static boolean isValidCode(String code) {
        return District.fromCode(code) != null;
    }
    
    /**
     * 유효한 서울특별시 구 이름인지 확인
     */
    public static boolean isValidName(String name) {
        return District.fromName(name) != null;
    }
}
