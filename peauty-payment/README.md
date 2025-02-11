# Payment Module

Payment Module은 Peauty 시스템 내에서 결제와 관련된 기능을 담당하는 모듈입니다.
현재는 Customer Module에서 Payment Module의 결제 기능을 사용하고 있지만, 향후에는 Designer Module에서도 이 기능을 사용할 수 있도록 확장될 예정입니다.
따라서 Payment Module은 특정 모듈에 종속되지 않고 독립적으로 결제 비즈니스 로직을 처리할 수 있도록 설계되어야 합니다.

## 주요 기능

- **결제 처리**: Payment Module 은 현재 Port One 을 사용하고 있습니다 추후 PG 사를 직접 연결하는 작업을 할 때, 이 모듈을 사용하는 모듈은 영향을 받지 않아야합니다.
- **에러 핸들링**: 타임아웃, 재시도 등 외부 API 통신과 관련된 기술적인 에러 처리를 Payment Module 내부에서 처리하여, 모듈 사용자가 이러한 에러 처리에 신경 쓰지 않아도 되도록 합니다.