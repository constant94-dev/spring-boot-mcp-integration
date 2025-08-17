# Spring Boot MCP Integration - 프로젝트 컨텍스트

## 📋 프로젝트 개요

Spring Boot 기반 MCP 통합 서버로, 공공데이터 API와 MCP 프로토콜을 연결하는 중간 계층 역할을 합니다. 멀티 모듈 구조로 설계되어 확장성과 유지보수성을 고려했습니다.

### 주요 기능
- **REST API 제공**: 외부 클라이언트를 위한 RESTful API
- **MCP 통합**: MCP 프로토콜과의 통신 처리
- **캐싱**: Redis를 통한 성능 최적화
- **세션 관리**: 사용자 세션 데이터 관리
- **공공데이터 API 연동**: 공공데이터포털 API 호출 및 처리

## ✅ 완료된 작업

### Phase 0: 프로젝트 구조 설정 (100% 완료)
- ✅ **Spring Boot Initializer 프로젝트 생성**: 3.4.0, Java 21 기반
- ✅ **멀티 모듈 구조 변환**: Web + Storage 모듈 구성
- ✅ **의존성 설정**: 단방향 의존성 (Web → Storage) 설정
- ✅ **기술 스택 설정**: Redis, JPA, Spring Security 등 설정
- ✅ **Thymeleaf 의존성 제거**: Vue.js 사용 예정으로 제거
- ✅ **CORS 지원 추가**: 프론트엔드 연동 준비
- ✅ **프로젝트 문서화**: README.md 및 다이어그램 완성
- ✅ **문서 구조 최적화**: 불필요한 파일 제거 및 핵심 다이어그램만 유지

### 기술 문서 완성
- ✅ **아키텍처 다이어그램**: 시스템 구조 및 데이터 플로우
- ✅ **시퀀스 다이어그램**: 통신 흐름 시각화
- ✅ **Mermaid 다이어그램**: 문법 오류 수정 및 최적화

## 🚀 앞으로 진행할 작업

### Phase 1: 백엔드 구현 (1-2주)
- [ ] **기본 엔티티 생성**
  - User 엔티티 (사용자 정보)
  - Session 엔티티 (세션 관리)
  - Log 엔티티 (로그 기록)
  - PublicData 엔티티 (공공데이터 정보)
- [ ] **JPA Repository 구현**
  - UserRepository
  - SessionRepository
  - LogRepository
  - PublicDataRepository
- [ ] **Redis 서비스 구현**
  - RedisCacheService
  - SessionCacheService
  - PublicDataCacheService
- [ ] **REST API 컨트롤러 구현**
  - UserController
  - SessionController
  - HealthController
  - PublicDataController
- [ ] **MCP 서버 연동**
  - MCP 클라이언트 설정
  - MCP 도구 호출 서비스
  - MCP 응답 처리 로직

### Phase 2: 설정 및 테스트 (1주)
- [ ] **application.yml 설정**
  - 데이터베이스 설정 (H2/PostgreSQL)
  - Redis 설정
  - 로깅 설정
- [ ] **단위 테스트 작성**
  - Service 계층 테스트
  - Repository 계층 테스트
- [ ] **API 연동 테스트**
  - REST API 엔드포인트 테스트
  - MCP 서버 연동 테스트

### Phase 3: 고급 기능 (2-3주)
- [ ] **보안 구현**
  - JWT 토큰 인증
  - Spring Security 설정
  - 역할 기반 접근 제어
- [ ] **모니터링 시스템**
  - Spring Boot Actuator 설정
  - 메트릭 수집
  - 헬스 체크
- [ ] **성능 최적화**
  - 캐싱 전략 구현
  - 데이터베이스 최적화
  - 응답 시간 개선

## 📊 현재 상태

### 기술 스택
- **Spring Boot**: 3.4.0
- **Java**: 21
- **Redis**: 캐싱 및 세션 저장
- **JPA**: 데이터베이스 접근
- **H2/PostgreSQL**: 데이터베이스
- **Gradle**: 빌드 도구

### 프로젝트 구조
```
spring-boot-mcp-integration/
├── build.gradle                    # 루트 빌드 설정
├── settings.gradle                 # 멀티 모듈 설정
├── web/                            # Web 모듈
│   ├── build.gradle               # Web 모듈 의존성
│   └── src/main/java/com/datapublic/mcp/web/
│       └── SpringBootMcpIntegrationApplication.java
├── storage/                        # Storage 모듈
│   ├── build.gradle               # Storage 모듈 의존성
│   └── src/main/java/com/datapublic/mcp/storage/
├── docs/                           # 문서
│   └── mermaid-preview/           # 다이어그램
│       ├── architecture.mmd       # 시스템 아키텍처
│       └── data-flow.mmd          # 데이터 플로우
└── README.md                       # 프로젝트 설명
```

### 모듈별 역할
- **Web 모듈**: REST API, 컨트롤러, 웹 설정
- **Storage 모듈**: 데이터 접근, 캐싱, 엔티티

### 성능 지표
- **목표 응답 시간**: < 200ms
- **목표 처리량**: 1000 TPS
- **메모리 사용량**: < 1GB

## ⚙️ 전역 설정 참조

이 프로젝트는 워크스페이스 루트의 전역 설정을 사용합니다:
- **전역 Cursor AI 규칙**: `/Users/ethan/Cursor/.cursor/.cursorrules`
- **전역 MCP 설정**: `/Users/ethan/Cursor/.cursor/mcp.json`
- **전역 프로젝트 개요**: `/Users/ethan/Cursor/cursor-workspace/PROJECTS.md`

## 🛠️ 기술 스택

### 현재 사용 중
- **Spring Boot**: 3.4.0
- **Java**: 21
- **Redis**: 캐싱 및 세션 저장
- **JPA**: 데이터베이스 접근
- **H2/PostgreSQL**: 데이터베이스
- **Gradle**: 빌드 도구

### 추가 예정
- **Spring Security**: 인증 및 인가
- **JWT**: 토큰 기반 인증
- **Spring Boot Actuator**: 모니터링
- **TestContainers**: 통합 테스트
- **MCP Java SDK**: MCP 서버 연동
- **WebClient**: HTTP 클라이언트

## 📁 프로젝트 구조

### 핵심 클래스
- **SpringBootMcpIntegrationApplication.java**: 메인 애플리케이션 클래스
  - Spring Boot 애플리케이션 시작점
  - 멀티 모듈 스캔 설정
  - 캐싱 및 JPA 활성화

### 향후 확장 계획
```
com.datapublic.mcp.web/
├── SpringBootMcpIntegrationApplication.java
├── controller/                      # REST API 컨트롤러
│   ├── UserController.java
│   ├── SessionController.java
│   └── HealthController.java
├── service/                         # 비즈니스 로직
│   ├── UserService.java
│   └── SessionService.java
├── dto/                            # 데이터 전송 객체
│   ├── UserDto.java
│   └── SessionDto.java
└── config/                         # 웹 설정
    ├── WebConfig.java
    └── SecurityConfig.java

com.datapublic.mcp.storage/
├── entity/                         # JPA 엔티티
│   ├── User.java
│   ├── Session.java
│   └── Log.java
├── repository/                     # JPA Repository
│   ├── UserRepository.java
│   ├── SessionRepository.java
│   └── LogRepository.java
├── service/                        # 저장소 서비스
│   ├── RedisCacheService.java
│   └── SessionCacheService.java
└── config/                         # 저장소 설정
    ├── RedisConfig.java
    └── JpaConfig.java
```

## 🔄 개발 과정

### 현재 워크플로우
1. **요구사항 분석**: API 스펙 및 데이터 모델 설계
2. **엔티티 설계**: JPA 엔티티 및 Repository 구현
3. **서비스 구현**: 비즈니스 로직 구현
4. **컨트롤러 구현**: REST API 엔드포인트 구현
5. **테스트 작성**: 단위 테스트 및 통합 테스트

### 새로운 기능 추가 방법
1. **엔티티 생성**: `storage/entity/NewEntity.java`
2. **Repository 생성**: `storage/repository/NewRepository.java`
3. **서비스 생성**: `web/service/NewService.java`
4. **컨트롤러 생성**: `web/controller/NewController.java`
5. **테스트 작성**: 각 계층별 단위 테스트

## 🔗 관련 프로젝트

- **[public-data-mcp-server](https://github.com/constant94-dev/public-data-mcp-server)**: MCP 서버
- **[vue-mcp-integration](https://github.com/constant94-dev/vue-mcp-integration)**: Vue.js 프론트엔드 클라이언트

## 📝 업데이트 히스토리

### 2025-08-17
- ✅ **Spring Boot 프로젝트 생성**: 3.4.0, Java 21 기반
- ✅ **멀티 모듈 구조 설정**: Web + Storage 모듈 구성
- ✅ **의존성 설정**: 단방향 의존성 및 기술 스택 설정
- ✅ **프로젝트 문서화**: README.md 및 다이어그램 완성
- ✅ **개발 환경 최적화**: CORS, 캐싱, 보안 설정 준비
- ✅ **문서 구조 최적화**: 불필요한 파일 제거 및 핵심 다이어그램만 유지
- ✅ **Mermaid 다이어그램 최적화**: 문법 오류 수정 및 가독성 향상

---

**마지막 업데이트**: 2025-08-17  
**작성자**: Ethan  
**상태**: 프로젝트 구조 완성 ✅ (백엔드 구현 준비됨)
