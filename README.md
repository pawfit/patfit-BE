### 반려견 미용 입찰 시스템 요구사항 살펴보기

- 고객은 한v 견적 제안서를 여러 미용사에게 보내면서 입찰을 시작할 수 있다
- 미용사는 본인에게 온 견적 제안서를 보고 견적서를 보낼 수 있다
- 고객은 한 견적 제안서에 온 여러 견적서 중 하나를 선택해 해당 견적서를 보낸 미용사와 미용 예약을 진행할 수 있다
- 미용사와 예약을 진행했다면 해당 견적 제안서에 견적서를 보낸 다른 미용사와 예약을 진행할 수 없다
- 진행 중이던 미용 예약을 취소하면 해당 견적 제안서에 견적서를 보낸 다른 미용사와 다시 미용 예약을 진행할 수 있다
- 미용 예약이 완료된 미용사와 오프라인에서 미용을 완료하면, 미용사가 해당 입찰을 완료할 수 있다

### 요구사항 해석 후 객체모델링
![스크린샷 2024-12-10 오후 1 46 13](https://github.com/user-attachments/assets/c70e1d25-f004-49cf-8e50-2879b338d9f2)

## Bidding Process Class

BiddingProcess는 입찰 프로세스의 전체 생명주기를 관리하는 도메인 클래스입니다. 각각의 입찰 프로세스는 하나의 강아지(Puppy)에 대한 여러 디자이너들의 입찰 스레드(BiddingThread)를 포함하고 있습니다.

## 주요 속성

- `id`: 입찰 프로세스의 고유 식별자 (ID record 타입)
- `puppyId`: 대상 강아지의 식별자 (PuppyId 타입)
- `status`: 현재 입찰 프로세스의 상태 (BiddingProcessStatus enum)
- `timeInfo`: 프로세스의 시간 정보 (BiddingProcessTimeInfo 타입)
- `threads`: 입찰 스레드 목록 (List<BiddingThread>)

## 생성 방법

1. 신규 프로세스 생성

```java
static BiddingProcess createNewProcess(PuppyId puppyId)

```

- 초기 상태: RESERVED_YET
- 빈 스레드 리스트로 시작
1. 기존 프로세스 로드

```java
static BiddingProcess loadProcess(ID id, PuppyId puppyId, BiddingProcessStatus status,
    BiddingProcessTimeInfo timeInfo, List<BiddingThread> threads)

```

## 주요 기능

### 스레드 관리

1. 스레드 추가

```java
void addNewThread(DesignerId targetDesignerId)

```

- 프로세스 상태 검증
- 중복 스레드 검사
- 옵저버 패턴을 통한 프로세스-스레드 연동
1. 스레드 조회

```java
BiddingThread getThread(BiddingThread.ID targetThreadId)
BiddingThread getThread(DesignerId targetThreadDesignerId)

```

### 상태 관리

1. 프로세스 취소

```java
void cancel()

```

1. 스레드 상태 변경
- 견적 응답: `responseEstimateThread()`
- 예약: `reserveThread()`
- 완료: `completeThread()`
- 취소: `cancelThread()`

### 이벤트 처리

- `onReservedThreadCancel()`: 예약된 스레드 취소 시 처리
- `onThreadReserved()`: 스레드 예약 시 처리
- `onThreadCompleted()`: 스레드 완료 시 처리

## 상태 검증

- 취소된 프로세스 검증
- 완료된 프로세스 검증
- 중복 스레드 검증

## 프로필 조회

```java
Profile getProfile(Puppy.Profile puppyProfile, Long designerId)

```

- 프로세스와 특정 디자이너의 스레드 정보를 포함한 프로필 생성

## Bidding Thread Class

BiddingThread는 개별 디자이너의 입찰 과정을 관리하는 도메인 클래스입니다. 견적 요청부터 완료까지의 전체 입찰 과정을 단계별로 관리하며, 소속된 BiddingProcess와 옵저버 패턴으로 연동됩니다.

## 주요 속성

- `id`: 입찰 스레드의 고유 식별자 (ID record 타입)
- `processId`: 소속 프로세스의 식별자 ([BiddingProcess.ID](http://biddingprocess.id/) 타입)
- `designerId`: 디자이너 식별자 (DesignerId 타입)
- `step`: 현재 입찰 단계 (BiddingThreadStep enum)
- `status`: 현재 스레드 상태 (BiddingThreadStatus enum)
- `timeInfo`: 스레드의 시간 정보 (BiddingThreadTimeInfo 타입)
- `belongingProcessObserver`: 소속 프로세스 참조 (BiddingProcess 타입)

## 생성 방법

1. 신규 스레드 생성

```java
static BiddingThread createNewThread(BiddingProcess.ID processId, DesignerId designerId)

```

- 초기 단계: ESTIMATE_REQUEST
- 초기 상태: NORMAL
- 옵저버 미등록 상태로 시작
1. 기존 스레드 로드

```java
static BiddingThread loadThread(ID id, BiddingProcess.ID processId, DesignerId designerId,
    BiddingThreadStep step, BiddingThreadStatus status, BiddingThreadTimeInfo timeInfo)

```

## 주요 기능

### 상태 관리

1. 단계 진행

```java
void responseEstimate()  // 견적 응답
void reserve()          // 예약
void complete()         // 완료
void cancel()           // 취소

```

1. 상태 변경

```java
void waiting()   // 대기 상태로 전환
void release()   // 대기 상태 해제

```

### 옵저버 관리

```java
void registerBelongingProcessObserver(BiddingProcess belongingProcess)

```

- 신규 생성 케이스와 기존 데이터 로드 케이스 구분 처리
- 프로세스-스레드 관계 검증

### 프로필 조회

```java
Profile getProfile(Designer.Profile designerProfile)

```

- 스레드와 디자이너 정보를 포함한 프로필 생성

## 상태 검증

- 단계 진행 가능 상태 검증
- 취소 가능 상태 검증
- 옵저버 등록 여부 검증
- 순차적 단계 진행 검증
