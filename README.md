# Peauty Modules Architecture

## Library Modules

### 🌐 Domain
- Peauty의 핵심 비즈니스 개념을 POJO로 표현
- 다른 모듈에 대한 종속성이 없는 독립적인 모듈

### 🔐 Security
- 공통 웹 보안 기능 제공
- 주요 기능:
  - Security Filters
  - Interceptors
  - Authentication/Authorization 처리

### 📝 Logging
- 공통 웹 로깅 기능 제공
- 일관된 로그 포맷 및 처리

### ☁️ AWS
- AWS 클라우드 관련 기능 제공
- 클라우드 서비스 통합 라이브러리

### 💾 Persistence
- 데이터 영속성 관리
- 현재 지원:
  - Single MySQL Instance

### 🚀 Cache
- 데이터 캐싱 기능 제공
- 현재 지원:
  - Single Redis Instance

## Application Modules

### 👥 Customer API
- 고객용 API 제공
- Customer 관련 비즈니스 로직 구현

### 💅 Designer API
- 디자이너용 API 제공
- Designer 관련 비즈니스 로직 구현
