# 공공데이터 포털 API 스펙 정의

## 1. 개요
공공데이터 포털(https://www.data.go.kr)에서 제공하는 Open API를 호출하는 서비스 스펙입니다.

## 2. 기본 API 구조

### 2.1 공공데이터 포털 API 기본 URL
```
https://apis.data.go.kr/{SERVICE_KEY}/{API_ENDPOINT}
```

### 2.2 공통 요청 파라미터
| 파라미터명 | 타입 | 필수 | 설명 |
|-----------|------|------|------|
| serviceKey | String | Y | 공공데이터 포털에서 발급받은 서비스키 |
| pageNo | Integer | N | 페이지 번호 (기본값: 1) |
| numOfRows | Integer | N | 한 페이지 결과 수 (기본값: 10) |
| type | String | N | 응답 타입 (json, xml) |

### 2.3 공통 응답 구조
```json
{
  "response": {
    "header": {
      "resultCode": "00",
      "resultMsg": "NORMAL SERVICE."
    },
    "body": {
      "items": {
        "item": []
      },
      "numOfRows": 10,
      "pageNo": 1,
      "totalCount": 100
    }
  }
}
```

## 3. 구현할 API 목록

### 3.1 대기오염정보 조회 API
- **API명**: 대기오염정보 조회
- **엔드포인트**: `/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty`
- **설명**: 시도별 실시간 측정정보 조회

#### 요청 파라미터
| 파라미터명 | 타입 | 필수 | 설명 |
|-----------|------|------|------|
| sidoName | String | Y | 시도명 |
| ver | String | N | 버전 |

#### 응답 데이터 구조
```json
{
  "dataTime": "2024-01-01 00:00",
  "itemCode": "PM10",
  "dataGubun": "일평균",
  "itemName": "미세먼지(PM10)",
  "sidoName": "서울",
  "stationName": "종로구",
  "value": "25"
}
```

### 3.2 지하철역 정보 조회 API
- **API명**: 지하철역 정보 조회
- **엔드포인트**: `/B551011/SwopenAPI/getSwopenApiInfo`
- **설명**: 지하철역 정보 조회

### 3.3 아파트 전월세 실거래가 조회 API (우선 구현)
- **API명**: 아파트 전월세 실거래가 조회
- **엔드포인트**: `/1613000/RTMSDataSvcAptRent`
- **설명**: 아파트 전월세 실거래가 정보 조회
- **참고**: [공공데이터 포털 API 계정 관리](https://www.data.go.kr/iim/api/selectAPIAcountView.do)

#### 요청 파라미터
| 파라미터명 | 타입 | 필수 | 설명 |
|-----------|------|------|------|
| serviceKey | String | Y | 공공데이터포털에서 발급받은 인증키 |
| LAWD_CD | String | Y | 각 지역별 코드 행정표준코드관리시스템(www.code.go.kr)의 법정동코드 10자리 중 앞 5자리 |
| DEAL_YMD | String | Y | 실거래 자료의 계약년월(6자리) |
| pageNo | Integer | N | 페이지번호 (기본값: 1) |
| numOfRows | Integer | N | 한 페이지 결과 수 (기본값: 10) |
| type | String | N | 응답 타입 (json, xml, 기본값: json) |

#### 응답 데이터 구조
```xml
<response>
<header>
<resultCode>000</resultCode>
<resultMsg>OK</resultMsg>
</header>
<body>
<items>
<item>
<aptNm>삼성</aptNm>
<buildYear>1998</buildYear>
<contractTerm/>
<contractType/>
<dealDay>25</dealDay>
<dealMonth>7</dealMonth>
<dealYear>2024</dealYear>
<deposit>29,768</deposit>
<excluUseAr>59.97</excluUseAr>
<floor>9</floor>
<jibun>596</jibun>
<monthlyRent>0</monthlyRent>
<preDeposit/>
<preMonthlyRent/>
<sggCd>11110</sggCd>
<umdNm>평창동</umdNm>
<useRRRight/>
</item>
</items>
<numOfRows>10</numOfRows>
<pageNo>1</pageNo>
<totalCount>96</totalCount>
</body>
</response>
```

#### 응답 필드 설명
| 필드명 | 타입 | 설명 |
|--------|------|------|
| aptNm | String | 아파트명 |
| buildYear | String | 건축년도 |
| contractTerm | String | 계약기간 |
| contractType | String | 계약형태 |
| dealDay | String | 계약일 |
| dealMonth | String | 계약월 |
| dealYear | String | 계약년 |
| deposit | String | 보증금 |
| excluUseAr | String | 전용면적 |
| floor | String | 층 |
| jibun | String | 지번 |
| monthlyRent | String | 월세 |
| preDeposit | String | 이전 보증금 |
| preMonthlyRent | String | 이전 월세 |
| sggCd | String | 시군구 코드 |
| umdNm | String | 읍면동명 |
| useRRRight | String | 사용승인일 |

#### API 호출 예시
```bash
# curl을 사용한 직접 호출
curl "https://apis.data.go.kr/1613000/RTMSDataSvcAptRent?serviceKey=YOUR_SERVICE_KEY&LAWD_CD=11680&DEAL_YMD=202401&type=json&pageNo=1&numOfRows=10"
```

### 3.4 날씨 정보 조회 API
- **API명**: 단기예보 조회
- **엔드포인트**: `/V1/ForecastSpaceData/getVilageFcst`
- **설명**: 단기예보 정보 조회

## 4. 에러 코드 정의

### 4.1 공공데이터 포털 에러 코드
| 코드 | 메시지 | 설명 |
|------|--------|------|
| 00 | NORMAL SERVICE. | 정상 |
| 01 | APPLICATION ERROR | 애플리케이션 에러 |
| 02 | DB ERROR | 데이터베이스 에러 |
| 03 | NO DATA | 데이터 없음 |
| 04 | HTTP ERROR | HTTP 에러 |
| 05 | SERVICETIMEOUT | 서비스 타임아웃 |
| 10 | INVALID REQUEST PARAMETER ERROR | 잘못된 요청 파라미터 |
| 11 | NO_MANDATORY_REQUEST_PARAMETERS_ERROR | 필수 요청 파라미터 없음 |
| 12 | NO_OPENAPI_SERVICE_ERROR | 해당 오픈 API 서비스가 없음 |
| 20 | SERVICE_ACCESS_DENIED_ERROR | 서비스 접근 거부 |
| 22 | LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR | 서비스 요청 제한 초과 |
| 30 | SERVICE_KEY_IS_NOT_REGISTERED_ERROR | 등록되지 않은 서비스키 |
| 31 | DEADLINE_HAS_EXPIRED_ERROR | 서비스키 만료 |
| 32 | UNREGISTERED_IP_ERROR | 등록되지 않은 IP |
| 33 | UNSUPPORTED_MOBILE_ERROR | 지원하지 않는 모바일 |
| 34 | UNSUPPORTED_IP_ERROR | 지원하지 않는 IP |
| 35 | NO_DATA_ERROR | 데이터 없음 |
| 99 | UNKNOWN_ERROR | 알 수 없는 오류 |

## 5. 구현 계획

### 5.1 1단계: 아파트 전월세 실거래가 API (우선 구현)
- [x] API 스펙 정의
- [x] DTO 클래스 생성
- [x] 서비스 클래스 구현
- [x] 컨트롤러 구현
- [x] 에러 처리 구현
- [x] 테스트 코드 작성

### 5.2 2단계: 추가 API 구현
- [ ] 대기오염정보 조회 API
- [ ] 지하철역 정보 조회 API
- [ ] 날씨 정보 조회 API

## 6. 설정

### 6.1 환경변수
```properties
# 공공데이터 포털 API 설정
public.data.service.key=YOUR_SERVICE_KEY
public.data.base.url=https://apis.data.go.kr
public.data.timeout=30
public.data.retry.count=3
```

### 6.2 의존성
```gradle
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'com.fasterxml.jackson.core:jackson-databind'
implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310'
```

## 7. 참고 자료

- [공공데이터 포털](https://www.data.go.kr)
- [API 계정 관리](https://www.data.go.kr/iim/api/selectAPIAcountView.do)
- [행정표준코드관리시스템](https://www.code.go.kr) (지역코드 확인)
- [아파트 전월세 실거래가 API 상세](https://www.data.go.kr/data/15058017/openapi.do)
