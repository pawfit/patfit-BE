### Peauty RDB 테이블 설계
![peauty_db](https://github.com/user-attachments/assets/fe7ec9eb-7cb0-4406-af40-3d3f45fd3715)
### Peauty 모듈 아키텍쳐
![퓨티 멀티모듈](https://github.com/user-attachments/assets/0d4979a0-9865-4d77-bd22-345e6b0edfd8)

### 반려견 미용 입찰 시스템 요구사항 살펴보기

- 고객은 한 견적 제안서를 여러 미용사에게 보내면서 입찰을 시작할 수 있다
- 미용사는 본인에게 온 견적 제안서를 보고 견적서를 보낼 수 있다
- 고객은 한 견적 제안서에 온 여러 견적서 중 하나를 선택해 해당 견적서를 보낸 미용사와 미용 예약을 진행할 수 있다
- 미용사와 예약을 진행했다면 해당 견적 제안서에 견적서를 보낸 다른 미용사와 예약을 진행할 수 없다
- 진행 중이던 미용 예약을 취소하면 해당 견적 제안서에 견적서를 보낸 다른 미용사와 다시 미용 예약을 진행할 수 있다
- 미용 예약이 완료된 미용사와 오프라인에서 미용을 완료하면, 미용사가 해당 입찰을 완료할 수 있다

### 요구사항 해석 후 도메인 모델링
![스크린샷 2024-12-10 오후 1 46 13](https://github.com/user-attachments/assets/c70e1d25-f004-49cf-8e50-2879b338d9f2)
