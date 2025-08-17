# Spring Boot MCP Integration

## 🎯 프로젝트 개요

Spring Boot 기반 MCP 통합 서버로, 공공데이터 API와 MCP 프로토콜을 연결하는 중간 계층 역할을 합니다.

### 주요 기능
- **REST API 제공**: 외부 클라이언트를 위한 RESTful API
- **MCP 통합**: MCP 프로토콜과의 통신 처리
- **캐싱**: Redis를 통한 성능 최적화
- **세션 관리**: 사용자 세션 데이터 관리

## 🏗️ 아키텍처

### 모듈 구조
```
web/                    # REST API 모듈
├── controller/         # REST API 엔드포인트
├── service/            # 비즈니스 로직
├── repository/         # 데이터 접근
├── dto/               # 데이터 전송 객체
├── config/            # 웹 설정
└── exception/         # 예외 처리

storage/                # 데이터 모듈
├── service/           # Redis 서비스
├── repository/        # JPA Repository
├── entity/            # JPA Entity
├── config/            # Redis/DB 설정
└── cache/             # 캐시 관리
```

### 데이터 플로우
1. **요청 수신** → 2. **검증** → 3. **캐시 확인** → 4. **비즈니스 로직** → 5. **캐시 저장** → 6. **응답 전송**

## 🛠️ 기술 스택

- **Spring Boot**: 3.4.0
- **Java**: 21
- **Redis**: 캐싱 및 세션 저장
- **JPA**: 데이터베이스 접근
- **H2/PostgreSQL**: 데이터베이스
- **Gradle**: 빌드 도구

## ✅ 완료된 작업

- ✅ Spring Boot Initializer로 프로젝트 생성 (3.4.0, Java 21)
- ✅ 멀티 모듈 구조로 변환 (Web + Storage)
- ✅ 캐시-데이터베이스 패턴 설계 (Redis + JPA)
- ✅ 의존성 설정 완료 (단방향: Web → Storage)
- ✅ Thymeleaf 의존성 제거 (Vue.js 사용 예정)
- ✅ CORS 지원 추가 (프론트엔드 연동 준비)

## 📋 다음 작업

### 1순위: 백엔드 구현
- [ ] 기본 엔티티 생성 (User, Session, Log 등)
- [ ] JPA Repository 구현
- [ ] Redis 서비스 구현
- [ ] REST API 컨트롤러 구현

### 2순위: 설정 및 테스트
- [ ] application.yml 설정
- [ ] 단위 테스트 작성
- [ ] API 연동 테스트

## 🚀 시작하기

### 요구사항
- Java 21+
- Gradle 8.0+
- Redis 6.0+

### 빌드 및 실행
```bash
# 전체 프로젝트 빌드
./gradlew buildAll

# Web 모듈 실행
./gradlew :web:bootRun

# 전체 테스트 실행
./gradlew testAll
```

## 🔗 관련 프로젝트

- **[vue-mcp-integration](https://github.com/constant94-dev/vue-mcp-integration)**: Vue.js 프론트엔드 클라이언트
- **[public-data-mcp-server](https://github.com/constant94-dev/public-data-mcp-server)**: 공공데이터 MCP 서버

---

**마지막 업데이트**: 2025-08-17  
**작성자**: Ethan  
**상태**: 프로젝트 구조 완성 ✅ (백엔드 구현 준비됨)
