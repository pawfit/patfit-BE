# Peauty Auth Module

Peauty Auth Module은 Peauty 시스템의 공통 웹 보안 기능을 제공하는 모듈입니다.
이 모듈은 Peauty 시스템의 API 모듈이 보안 관련 기능을 직접 구현하지 않고도 동작할 수 있도록 지원합니다.
Peauty Auth Module의 주요 책임은 API 모듈의 보안 관련 종속성을 제거하여 API 모듈이 본연의 기능에만 집중할 수 있게 하는 것입니다.

## 주요 기능

- **인증 및 인가 처리**: 사용자 인증 및 권한 검사를 수행하여 API 엔드포인트에 대한 접근을 제어합니다. JWT(JSON Web Token) 등의 인증 방식을 지원하고, 사용자 역할에 따른 세부적인 권한 제어 기능을 제공합니다.

- **Filter 및 Interceptor 제공**: 요청 및 응답에 대한 전처리 및 후처리를 수행하는 Filter와 Interceptor를 제공합니다.