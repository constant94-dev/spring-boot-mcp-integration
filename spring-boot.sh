#!/bin/bash

# Spring Boot MCP Integration 통합 스크립트
# 비즈니스 로직과 테스트 코드를 빌드하고 실행하며 성공/실패를 판단

set -e

# 색상 정의
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 로그 함수
log_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

log_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

log_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

log_error() {
    echo -e "${RED}❌ $1${NC}"
}

# 초기화
cd "$(dirname "$0")"
mkdir -p logs

LOG_DIR="logs"
LOG_FILE="${LOG_DIR}/spring-boot-$(date +%Y%m%d_%H%M%S).log"

# 로그 파일에 출력하는 함수
log_to_file() {
    echo "$1" | tee -a "$LOG_FILE"
}

# 메인 함수들
build_project() {
    log_info "🔨 프로젝트 빌드 중..."
    log_to_file "🔨 프로젝트 빌드 시작: $(date)"
    
    if ./gradlew clean build -q; then
        log_success "빌드 성공!"
        log_to_file "✅ 빌드 성공: $(date)"
        return 0
    else
        log_error "빌드 실패!"
        log_to_file "❌ 빌드 실패: $(date)"
        return 1
    fi
}

run_tests() {
    local test_type="$1"
    log_info "🧪 테스트 실행 중... ($test_type)"
    log_to_file "🧪 테스트 실행 시작 ($test_type): $(date)"
    
    case "$test_type" in
        "api")
            ./gradlew test --tests ApartmentRentServiceTest.testGetApartmentRentData --rerun-tasks --console=plain --info 2>&1 | tee -a "$LOG_FILE"
            ;;
        "unit")
            ./gradlew test --tests ApartmentRentServiceTest --rerun-tasks --console=plain --info 2>&1 | tee -a "$LOG_FILE"
            ;;
        "all")
            ./gradlew test --rerun-tasks --console=plain --info 2>&1 | tee -a "$LOG_FILE"
            ;;
        *)
            ./gradlew test --tests ApartmentRentServiceTest.testGetApartmentRentData --rerun-tasks --console=plain --info 2>&1 | tee -a "$LOG_FILE"
            ;;
    esac
    
    local test_exit_code=${PIPESTATUS[0]}
    
    if [ $test_exit_code -eq 0 ]; then
        log_success "테스트 성공!"
        log_to_file "✅ 테스트 성공 ($test_type): $(date)"
        return 0
    else
        log_error "테스트 실패! (종료 코드: $test_exit_code)"
        log_to_file "❌ 테스트 실패 ($test_type): $(date)"
        return 1
    fi
}

start_server() {
    local mode="$1"
    log_info "🚀 Spring Boot 서버 시작 중... ($mode)"
    log_to_file "🚀 서버 시작 ($mode): $(date)"
    
    if [ "$mode" = "background" ]; then
        # JAR 파일 확인 및 빌드
        if [ ! -f "web/build/libs/web-0.0.1-SNAPSHOT.jar" ]; then
            log_info "JAR 파일을 빌드합니다..."
            build_project
        fi
        
        # 기존 프로세스 종료
        pkill -f "web-0.0.1-SNAPSHOT.jar" || true
        
        # 백그라운드 실행
        nohup java -jar web/build/libs/web-0.0.1-SNAPSHOT.jar > logs/spring-boot-server-$(date +%Y%m%d_%H%M%S).log 2>&1 &
        
        log_success "서버가 백그라운드에서 시작되었습니다."
        log_to_file "✅ 백그라운드 서버 시작: $(date)"
        
        # 헬스체크
        sleep 5
        if curl -s http://localhost:8080/api/health > /dev/null; then
            log_success "서버가 정상적으로 실행되었습니다!"
            log_to_file "✅ 서버 헬스체크 성공: $(date)"
            return 0
        else
            log_error "서버 시작에 실패했습니다. 로그를 확인하세요."
            log_to_file "❌ 서버 헬스체크 실패: $(date)"
            return 1
        fi
    else
        # 포그라운드 실행
        ./gradlew bootRun 2>&1 | tee logs/spring-boot-server-$(date +%Y%m%d_%H%M%S).log
    fi
}

show_logs() {
    log_info "📁 로그 파일 목록"
    log_to_file "📁 로그 파일 목록 조회: $(date)"
    
    if [ ! -d "$LOG_DIR" ]; then
        log_warning "로그 디렉토리가 존재하지 않습니다: $LOG_DIR"
        return 1
    fi
    
    if [ -z "$(ls -A $LOG_DIR)" ]; then
        log_warning "로그 파일이 없습니다."
        return 1
    fi
    
    echo "📋 사용 가능한 로그 파일:"
    ls -lh "$LOG_DIR"/*.log | while read line; do
        echo "$line"
    done
    
    echo ""
    echo "🔍 로그 확인 명령어:"
    echo "tail -f $LOG_DIR/\$(ls -t $LOG_DIR/*.log | head -1)  # 최신 로그 확인"
    echo "cat $LOG_DIR/[파일명]                                # 특정 로그 확인"
    echo "grep '키워드' $LOG_DIR/*.log                         # 로그 검색"
}

show_help() {
    echo "Spring Boot MCP Integration 통합 스크립트"
    echo ""
    echo "사용법: $0 [명령] [옵션]"
    echo ""
    echo "명령:"
    echo "  build                    # 프로젝트 빌드"
    echo "  test [타입]              # 테스트 실행"
    echo "  start [모드]             # 서버 시작"
    echo "  logs                     # 로그 파일 목록"
    echo "  all                      # 빌드 + 테스트 + 서버 시작"
    echo "  help                     # 도움말 표시"
    echo ""
    echo "테스트 타입:"
    echo "  api                      # API 테스트 (기본값)"
    echo "  unit                     # 단위 테스트"
    echo "  all                      # 전체 테스트"
    echo ""
    echo "서버 모드:"
    echo "  foreground               # 포그라운드 실행 (기본값)"
    echo "  background               # 백그라운드 실행"
    echo ""
    echo "예시:"
    echo "  $0 build                 # 프로젝트 빌드"
    echo "  $0 test api              # API 테스트 실행"
    echo "  $0 start background      # 백그라운드에서 서버 시작"
    echo "  $0 all                   # 전체 프로세스 실행"
    echo "  $0 logs                  # 로그 파일 목록"
}

# 메인 실행 로직
main() {
    local command="$1"
    local option="$2"
    
    case "$command" in
        "build")
            build_project
            ;;
        "test")
            run_tests "$option"
            ;;
        "start")
            start_server "$option"
            ;;
        "logs")
            show_logs
            ;;
        "all")
            log_info "🔄 전체 프로세스 실행 중..."
            log_to_file "🔄 전체 프로세스 시작: $(date)"
            
            # 빌드
            if ! build_project; then
                log_error "빌드 실패로 중단"
                exit 1
            fi
            
            # 테스트
            if ! run_tests "api"; then
                log_error "테스트 실패로 중단"
                exit 1
            fi
            
            # 서버 시작
            if ! start_server "background"; then
                log_error "서버 시작 실패"
                exit 1
            fi
            
            log_success "전체 프로세스 완료!"
            log_to_file "✅ 전체 프로세스 완료: $(date)"
            ;;
        "help"|"")
            show_help
            ;;
        *)
            log_error "알 수 없는 명령: $command"
            show_help
            exit 1
            ;;
    esac
}

# 스크립트 시작
log_to_file "🚀 Spring Boot MCP Integration 스크립트 시작: $(date)"
main "$@"
