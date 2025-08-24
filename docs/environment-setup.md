# 환경변수 설정 가이드

## 개요
공공데이터 포털 API를 사용하기 위한 환경변수 설정 방법을 안내합니다.

## 1. 공공데이터 포털 서비스키 발급

### 1.1 공공데이터 포털 접속
- [공공데이터 포털](https://www.data.go.kr) 접속
- 로그인 또는 회원가입

### 1.2 API 신청
1. [API 계정 관리](https://www.data.go.kr/iim/api/selectAPIAcountView.do) 페이지 접속
2. 원하는 API 검색 (예: "아파트 전월세 실거래가")
3. API 신청 및 승인 대기

### 1.3 서비스키 확인
- 승인 후 "서비스키" 확인
- 일반적으로 32자리 문자열 형태

## 2. 환경변수 설정

### 2.1 자동 설정 (권장)
```bash
# 환경변수 설정 스크립트 실행
./set-env.sh
```

### 2.2 수동 설정
1. `env/env.local` 파일 편집:
```bash
nano env/env.local
```

2. 서비스키 설정:
```bash
export PUBLIC_DATA_SERVICE_KEY="실제_발급받은_서비스키"
```

### 2.3 환경변수 목록

| 변수명 | 설명 | 기본값 | 필수 |
|--------|------|--------|------|
| `PUBLIC_DATA_SERVICE_KEY` | 공공데이터 포털 서비스키 | - | ✅ |
| `PUBLIC_DATA_BASE_URL` | API 기본 URL | https://apis.data.go.kr | ❌ |
| `PUBLIC_DATA_TIMEOUT` | 타임아웃 (초) | 30 | ❌ |
| `PUBLIC_DATA_RETRY_COUNT` | 재시도 횟수 | 3 | ❌ |
| `SERVER_PORT` | Spring Boot 서버 포트 | 8080 | ❌ |

## 3. 설정 확인

### 3.1 환경변수 확인
```bash
# 스크립트로 확인
./set-env.sh

# 로컬 환경 설정 로드
source env/env.local

# 또는 직접 확인
echo $PUBLIC_DATA_SERVICE_KEY
```

### 3.2 애플리케이션 실행
```bash
# 로컬 환경으로 실행 (권장)
./run-local.sh

# 또는 Gradle로 직접 실행
./gradlew bootRun --args="--spring.profiles.active=local"

# 또는 JAR 파일로 실행
./gradlew build
java -jar web/build/libs/web-*.jar --spring.profiles.active=local
```

## 4. API 테스트

### 4.1 서버 상태 확인
```bash
curl http://localhost:8080/api/public-data/status
```

### 4.2 서울특별시 구 목록 조회
```bash
curl http://localhost:8080/api/public-data/districts
```

### 4.3 아파트 전월세 실거래가 조회
```bash
# 지역코드로 조회
curl "http://localhost:8080/api/public-data/apartment-rent?districtCode=11680&dealYearMonth=202401"

# 구명으로 조회
curl "http://localhost:8080/api/public-data/apartment-rent/district?districtName=강남구&dealYearMonth=202401"
```

## 5. 문제 해결

### 5.1 서비스키 오류
```
❌ 오류: PUBLIC_DATA_SERVICE_KEY가 설정되지 않았습니다!
```
**해결방법:**
- `.env` 파일에서 서비스키가 올바르게 설정되었는지 확인
- 공공데이터 포털에서 서비스키가 유효한지 확인

### 5.2 API 호출 실패
```
❌ API 호출 실패 - HTTP 상태: 401
```
**해결방법:**
- 서비스키가 올바른지 확인
- API 신청이 승인되었는지 확인
- 일일 호출 한도를 초과하지 않았는지 확인

### 5.3 타임아웃 오류
```
❌ API 호출 중 타임아웃 발생
```
**해결방법:**
- `PUBLIC_DATA_TIMEOUT` 값을 늘려서 설정
- 네트워크 연결 상태 확인

## 6. 보안 주의사항

### 6.1 환경변수 파일 보안
- `env/.env`, `env/env.local` 파일은 Git에 커밋하지 않음
- `.gitignore`에 환경변수 파일들 추가됨
- 서비스키를 코드에 하드코딩하지 않음

### 6.2 서비스키 관리
- 서비스키를 공개하지 않음
- 정기적으로 서비스키 갱신
- 필요시 서비스키 재발급

## 7. 추가 리소스

- [공공데이터 포털](https://www.data.go.kr)
- [API 계정 관리](https://www.data.go.kr/iim/api/selectAPIAcountView.do)
- [행정표준코드관리시스템](https://www.code.go.kr) (지역코드 확인)
