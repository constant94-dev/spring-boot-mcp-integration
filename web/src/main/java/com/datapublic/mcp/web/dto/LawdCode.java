package com.datapublic.mcp.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 법정동코드 정보 DTO
 * 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LawdCode {
    
    /**
     * 지역코드 (5자리)
     */
    private String code;
    
    /**
     * 지역명
     */
    private String name;
    
    /**
     * 시도명
     */
    private String sidoName;
    
    /**
     * 시군구명
     */
    private String sigunguName;
    
    /**
     * 서울특별시 지역코드 상수
     * 법정동코드 10자리 중 앞 5자리
     */
    public static class SeoulCodes {
        public static final String JONGNO = "11110"; // 종로구
        public static final String JUNG = "11140"; // 중구
        public static final String YONGSAN = "11170"; // 용산구
        public static final String SEONGDONG = "11200"; // 성동구
        public static final String GWANGJIN = "11215"; // 광진구
        public static final String DONGDAEMUN = "11230"; // 동대문구
        public static final String JUNGNANG = "11260"; // 중랑구
        public static final String SEONGBUK = "11290"; // 성북구
        public static final String GANGBUK = "11305"; // 강북구
        public static final String DOBONG = "11320"; // 도봉구
        public static final String NOWON = "11350"; // 노원구
        public static final String EUNPYEONG = "11380"; // 은평구
        public static final String SEODAEMUN = "11410"; // 서대문구
        public static final String MAPO = "11440"; // 마포구
        public static final String YANGCHEON = "11470"; // 양천구
        public static final String GANGSEO = "11500"; // 강서구
        public static final String GURO = "11530"; // 구로구
        public static final String GEUMCHEON = "11545"; // 금천구
        public static final String YEONGDEUNGPO = "11560"; // 영등포구
        public static final String DONGJAK = "11590"; // 동작구
        public static final String GWANAK = "11620"; // 관악구
        public static final String SEOCHO = "11650"; // 서초구
        public static final String GANGNAM = "11680"; // 강남구
        public static final String SONGPA = "11710"; // 송파구
        public static final String GANGDONG = "11740"; // 강동구
    }
    
    /**
     * 주요 지역코드 상수 (기존 호환성 유지)
     */
    public static class Codes {
        // 서울특별시
        public static final String GANGNAM = "11680"; // 강남구
        public static final String GANGDONG = "11740"; // 강동구
        public static final String GANGBUK = "11305"; // 강북구
        public static final String GANGSEO = "11500"; // 강서구
        public static final String GWANAK = "11620"; // 관악구
        public static final String GWANGJIN = "11215"; // 광진구
        public static final String GURO = "11530"; // 구로구
        public static final String GEUMCHEON = "11545"; // 금천구
        public static final String NOWON = "11350"; // 노원구
        public static final String DOBONG = "11320"; // 도봉구
        public static final String DONGDAEMUN = "11230"; // 동대문구
        public static final String DONGJAK = "11590"; // 동작구
        public static final String MAPO = "11440"; // 마포구
        public static final String SEODAEMUN = "11410"; // 서대문구
        public static final String SEOCHO = "11650"; // 서초구
        public static final String SEONGDONG = "11200"; // 성동구
        public static final String SEONGBUK = "11290"; // 성북구
        public static final String SONGPA = "11710"; // 송파구
        public static final String YANGCHEON = "11470"; // 양천구
        public static final String YEONGDEUNGPO = "11560"; // 영등포구
        public static final String YONGSAN = "11170"; // 용산구
        public static final String EUNPYEONG = "11380"; // 은평구
        public static final String JONGNO = "11110"; // 종로구
        public static final String JUNG = "11140"; // 중구
        public static final String JUNGNANG = "11260"; // 중랑구
        
        // 경기도
        public static final String SUWON = "41110"; // 수원시
        public static final String SEONGNAM = "41130"; // 성남시
        public static final String UIJEONGBU = "41150"; // 의정부시
        public static final String ANYANG = "41170"; // 안양시
        public static final String BUCHEON = "41190"; // 부천시
        public static final String GWANGMYEONG = "41210"; // 광명시
        public static final String PYEONGTAEK = "41220"; // 평택시
        public static final String DONGDUCHEON = "41250"; // 동두천시
        public static final String ANSAN = "41270"; // 안산시
        public static final String HWASEONG = "41280"; // 화성시
        public static final String GWANGJU = "41610"; // 광주시
        public static final String YONGIN = "41430"; // 용인시
        public static final String PAJU = "41460"; // 파주시
        public static final String ICHEON = "41480"; // 이천시
        public static final String ANSEONG = "41550"; // 안성시
        public static final String KIMPO = "41570"; // 김포시
        public static final String HWAJEONG = "41590"; // 화성시
        public static final String YEOJU = "41630"; // 여주시
        public static final String YANGPYEONG = "41730"; // 양평군
        public static final String GAPYEONG = "41820"; // 가평군
        public static final String YANGJU = "41650"; // 양주시
        public static final String NAMYANGJU = "41670"; // 남양주시
        public static final String OSAN = "41360"; // 오산시
        public static final String SIHEUNG = "41390"; // 시흥시
        public static final String GUNPO = "41410"; // 군포시
        public static final String UIWANG = "41450"; // 의왕시
        public static final String HANAM = "41470"; // 하남시
        public static final String GURI = "41310"; // 구리시
        public static final String NAMYANGJU_OLD = "41330"; // 남양주시(구)
        public static final String POCHEON = "41620"; // 포천시
        public static final String YEONCHEON = "41830"; // 연천군
    }
}

