-- Workspace 테이블 데이터
INSERT INTO workspace
(designer_id, introduce_title, introduce, notice_title, notice, address, address_detail, workspace_name,
 open_hours, close_hours, open_days, direction_guide, review_count, review_rating,
 phone_number, created_at, updated_at)
VALUES
    (1, '따뜻한 미용실에서 편안한 시간 보내세요', '10년 경력의 전문 펫미용사가 안전하고 편안한 미용을 약속드립니다.', '12월 연말 이벤트', '첫 방문 고객님 10% 할인해드립니다!', '서울시 강남구 역삼동', '럭키빌딩 2층 201호', '스타일하우스', '10:00', '20:00', 'MON-SAT', '2번 출구에서 도보 5분', 45, 4.8, '02-555-1234', NOW(), NOW()),
    (2, '우리 아이를 위한 특별한 공간', '반려동물 행동 전문가와 함께하는 스트레스 없는 미용', '신규 오픈 기념 이벤트', '목욕 서비스 20% 할인', '서울시 서초구 서초동', '그린빌딩 1층', '펫살롱', '09:00', '19:00', 'TUE-SUN', '1번 출구 바로 앞', 32, 4.7, '02-666-5678', NOW(), NOW()),
    (3, '전문가의 손길로 더욱 예쁘게', '20년 경력 펫미용 전문가의 섬세한 미용', '주차 안내', '건물 내 2시간 무료주차', '서울시 송파구 잠실동', '블루빌딩 3층', '댕댕미용실', '11:00', '21:00', 'MON-SUN', '잠실역 4번 출구 도보 3분', 67, 4.9, '02-777-9012', NOW(), NOW()),
    (4, '당신의 반려견을 위한 힐링공간', '반려동물 스트레스 케어 전문가가 함께합니다', '겨울 스페셜 케어', '발바닥 보습 케어 서비스 증정', '서울시 강남구 삼성동', '펫타워 4층', '멍멍살롱', '09:30', '19:30', 'MON-FRI', '삼성중앙역 1번 출구 도보 1분', 89, 4.6, '02-888-3456', NOW(), NOW()),
    (5, '프리미엄 펫 케어 살롱', '최고급 설비와 전문 스태프가 모신 프리미엄 살롱', '신규 회원 이벤트', '방문 시 케어용품 증정', '서울시 마포구 합정동', '테라스원 B동 2층', '라포미용실', '10:30', '20:30', 'TUE-SUN', '합정역 5번 출구 도보 3분', 78, 4.8, '02-999-7890', NOW(), NOW()),
    (6, '우리 동네 착한 미용실', '합리적인 가격으로 최상의 서비스를 제공합니다', '평일 할인', '평일 방문 시 5% 할인', '서울시 영등포구 여의도동', '미소빌딩 1층', '해피도그', '10:00', '19:00', 'MON-SAT', '여의도역 3번 출구 도보 5분', 56, 4.5, '02-111-2345', NOW(), NOW()),
    (7, '전문 펫 스타일리스트', '각 견종별 전문 미용사가 맞춤형 미용을 제공합니다', '여름 이벤트', '시원한 스파 서비스 추가', '서울시 강서구 마곡동', '펫프라자 3층', '아트펫', '11:00', '20:00', 'WED-SUN', '마곡나루역 2번 출구 도보 7분', 43, 4.7, '02-222-6789', NOW(), NOW()),
    (8, '프로페셔널 그루밍샵', '미국 자격증 보유 전문가의 섬세한 미용', '겨울맞이 이벤트', '털깎기 10% 할인', '서울시 용산구 이태원동', '그루밍타워 5층', '프로펫', '09:00', '18:00', 'MON-SAT', '이태원역 1번 출구 도보 4분', 92, 4.9, '02-333-0123', NOW(), NOW()),
    (9, '우리 아이 전용 뷰티샵', '반려동물 심리상담사가 상주하는 편안한 미용실', '초특가 이벤트', '대형견 미용 20% 할인', '서울시 성동구 성수동', '애견빌딩 2층', '러블리펫', '10:30', '19:30', 'TUE-SUN', '성수역 4번 출구 도보 2분', 67, 4.6, '02-444-4567', NOW(), NOW()),
    (10, '프리미엄 펫 뷰티센터', '최신 설비 완비, 전문가의 1:1 맞춤 케어', 'VIP 회원 모집', '연회원 가입시 30% 할인', '서울시 광진구 구의동', '펫케어센터 1층', '뷰티멍멍', '11:30', '20:30', 'MON-SUN', '구의역 2번 출구 도보 1분', 88, 4.8, '02-555-8901', NOW(), NOW()),
    (11, '반려견 토탈 케어샵', '미용부터 호텔링까지 원스톱 케어서비스', '호텔링 패키지', '미용시 호텔링 50% 할인', '서울시 동대문구 장안동', '펫타워 3층', '토탈케어', '09:00', '21:00', 'MON-SUN', '장안동역 1번 출구 도보 3분', 75, 4.7, '02-666-2345', NOW(), NOW()),
    (12, '프로 애견미용', '30년 전통의 신뢰할 수 있는 미용실', '신규 고객 이벤트', '첫 방문 15% 할인', '서울시 중구 을지로동', '센트럴프라자 2층', '명품살롱', '10:00', '19:00', 'TUE-SUN', '을지로입구역 4번 출구 도보 5분', 91, 4.9, '02-777-6789', NOW(), NOW()),
    (13, '스마트 펫 케어', '첨단 장비와 전문 미용사의 만남', '앱 예약 이벤트', '앱 예약시 5% 추가할인', '서울시 종로구 종로동', '스마트빌딩 4층', '테크펫', '09:30', '18:30', 'MON-FRI', '종각역 3번 출구 도보 2분', 45, 4.5, '02-888-0123', NOW(), NOW()),
    (14, '프리미엄 펫 살롱', '럭셔리한 공간에서 특별한 경험을', '겨울 스페셜', '스파 서비스 증정', '서울시 강북구 수유동', '펫플렉스 1층', '로얄펫', '11:00', '20:00', 'MON-SAT', '수유역 2번 출구 도보 1분', 63, 4.8, '02-999-4567', NOW(), NOW()),
    (15, '우리동네 펫 살롱', '친절하고 꼼꼼한 미용 서비스', '주말 특가', '주말 예약시 10% 할인', '서울시 노원구 상계동', '애견프라자 2층', '친절살롱', '10:30', '19:30', 'TUE-SUN', '노원역 1번 출구 도보 5분', 82, 4.6, '02-111-8901', NOW(), NOW()),
    (16, '힐링 펫 살롱', '스트레스 없는 편안한 미용', '봄맞이 이벤트', '목욕 서비스 30% 할인', '서울시 은평구 불광동', '힐링센터 3층', '힐링타임', '09:00', '20:00', 'MON-SAT', '불광역 4번 출구 도보 3분', 54, 4.7, '02-222-2345', NOW(), NOW()),
    (17, '클래식 펫 그루밍', '클래식한 분위기의 고급 미용실', '디자인 컷 이벤트', '디자인 컷 20% 할인', '서울시 서대문구 신촌동', '클래식빌딩 5층', '클래식멍', '10:00', '21:00', 'MON-SUN', '신촌역 1번 출구 도보 2분', 77, 4.9, '02-333-6789', NOW(), NOW()),
    (18, '퍼스트 펫 케어', '최고의 서비스를 최선의 가격으로', 'VIP 멤버십', '연간 회원 특별 할인', '서울시 관악구 봉천동', '퍼스트타워 2층', '퍼스트샵', '11:30', '20:30', 'TUE-SUN', '서울대입구역 3번 출구 도보 5분', 69, 4.8, '02-444-0123', NOW(), NOW()),
    (19, '모던 펫 살롱', '현대적 감각의 프리미엄 살롱', '겨울 패키지', '전신 미용 패키지 할인', '서울시 금천구 가산동', '모던플라자 1층', '모던독', '09:30', '19:30', 'MON-SAT', '가산디지털단지역 2번 출구 도보 3분', 48, 4.5, '02-555-4567', NOW(), NOW()),
    (20, '펫 뷰티 스페이스', '넓고 쾌적한 공간의 프리미엄 살롱', '신규 오픈', '전 서비스 30% 할인', '서울시 도봉구 쌍문동', '뷰티타워 4층', '뷰티스페이스', '10:00', '20:00', 'MON-SUN', '쌍문역 1번 출구 도보 1분', 35, 4.7, '02-666-8901', NOW(), NOW()),
    (21, '아트 펫 디자인', '예술적 감각의 펫 디자인 전문점', '아트 스타일링', '디자인 컷 특별 할인', '서울시 양천구 목동', '아트센터 3층', '아트독', '11:00', '21:00', 'TUE-SUN', '목동역 4번 출구 도보 2분', 86, 4.9, '02-777-2345', NOW(), NOW()),
    (22, '네이처 펫 케어', '자연친화적 제품만을 사용하는 미용실', '친환경 케어', '천연 샴푸 서비스 무료', '서울시 구로구 구로동', '네이처빌딩 2층', '네이처펫', '09:00', '19:00', 'MON-SAT', '구로디지털단지역 2번 출구 도보 5분', 72, 4.6, '02-888-6789', NOW(), NOW()),
    (23, '럭셔리 펫 살롱', '최고급 설비와 서비스의 품격있는 살롱', 'VIP 서비스', '스파 패키지 20% 할인', '서울시 동작구 사당동', '럭셔리타워 5층', '럭셔리펫', '10:30', '20:30', 'MON-SUN', '사당역 1번 출구 도보 3분', 94, 4.8, '02-999-0123', NOW(), NOW()),
    (24, '홈 스타일 펫샵', '우리 집처럼 편안한 미용실', '편안함이 우선', '향기테라피 서비스 무료', '서울시 성북구 길음동', '홈스타일빌딩 1층', '홈스타일', '10:00', '19:00', 'MON-SAT', '길음역 3번 출구 도보 4분', 52, 4.7, '02-111-4567', NOW(), NOW()),
    (25, '슈퍼 펫 케어', '최첨단 장비를 갖춘 미용실', '첨단 케어', '적외선 테라피 50% 할인', '서울시 중랑구 면목동', '슈퍼타워 2층', '슈퍼펫', '09:30', '19:30', 'TUE-SUN', '면목역 1번 출구 도보 2분', 67, 4.5, '02-222-8901', NOW(), NOW()),
    (26, '퀸즈 펫 살롱', '여왕님처럼 모시는 프리미엄 케어', '퀸즈 스페셜', '럭셔리 풀케어 10% 할인', '서울시 강동구 천호동', '퀸즈프라자 4층', '퀸즈살롱', '11:00', '20:00', 'MON-SUN', '천호역 2번 출구 도보 1분', 83, 4.9, '02-333-2345', NOW(), NOW()),
    (27, '하이엔드 펫 케어', '최고급 서비스를 약속드립니다', '프리미엄 케어', '스파 테라피 패키지 할인', '서울시 동대문구 이문동', '하이엔드몰 3층', '하이엔드', '10:30', '20:30', 'MON-SAT', '이문역 4번 출구 도보 3분', 61, 4.8, '02-444-6789', NOW(), NOW()),
    (28, '케어풀 펫 살롱', '세심한 케어로 보답하겠습니다', '케어풀 특가', '발톱 케어 무료', '서울시 성동구 행당동', '케어풀타워 2층', '케어풀', '09:00', '18:00', 'TUE-SUN', '행당역 1번 출구 도보 5분', 45, 4.6, '02-555-0123', NOW(), NOW()),
    (29, '센트럴 펫 케어', '도심 속 최고의 펫케어 센터', '센트럴 패키지', '전신 미용 패키지 20% 할인', '서울시 용산구 한남동', '센트럴프라자 5층', '센트럴살롱', '10:00', '21:00', 'MON-SUN', '한남역 2번 출구 도보 2분', 78, 4.7, '02-666-4567', NOW(), NOW()),
    (30, '엘리트 펫 살롱', '최고의 전문가들이 모인 프리미엄 살롱', '엘리트 스페셜', '컷 & 스파 패키지 30% 할인', '서울시 마포구 서교동', '엘리트빌딩 1층', '엘리트살롱', '11:30', '21:30', 'MON-SAT', '홍대입구역 3번 출구 도보 4분', 96, 4.8, '02-777-8901', NOW(), NOW());

-- 모든 workspace의 결제 옵션 데이터
INSERT INTO workspace_entity_payment_options (workspace_entity_id, payment_options) VALUES
                                                                          (1, 'CARD'), (1, 'CASH'), (1, 'ACCOUNT'),
                                                                          (2, 'CARD'), (2, 'CASH'),
                                                                          (3, 'CARD'), (3, 'ACCOUNT'),
                                                                          (4, 'CARD'), (4, 'CASH'), (4, 'ACCOUNT'),
                                                                          (5, 'CARD'), (5, 'ACCOUNT'),
                                                                          (6, 'CARD'), (6, 'CASH'),
                                                                          (7, 'CARD'), (7, 'CASH'), (7, 'ACCOUNT'),
                                                                          (8, 'CARD'), (8, 'ACCOUNT'),
                                                                          (9, 'CARD'), (9, 'CASH'),
                                                                          (10, 'CARD'), (10, 'CASH'), (10, 'ACCOUNT'),
                                                                          (11, 'CARD'), (11, 'ACCOUNT'),
                                                                          (12, 'CARD'), (12, 'CASH'),
                                                                          (13, 'CARD'), (13, 'CASH'), (13, 'ACCOUNT'),
                                                                          (14, 'CARD'), (14, 'ACCOUNT'),
                                                                          (15, 'CARD'), (15, 'CASH'),
                                                                          (16, 'CARD'), (16, 'CASH'), (16, 'ACCOUNT'),
                                                                          (17, 'CARD'), (17, 'ACCOUNT'),
                                                                          (18, 'CARD'), (18, 'CASH'),
                                                                          (19, 'CARD'), (19, 'CASH'), (19, 'ACCOUNT'),
                                                                          (20, 'CARD'), (20, 'ACCOUNT'),
                                                                          (21, 'CARD'), (21, 'CASH'),
                                                                          (22, 'CARD'), (22, 'CASH'), (22, 'ACCOUNT'),
                                                                          (23, 'CARD'), (23, 'ACCOUNT'),
                                                                          (24, 'CARD'), (24, 'CASH'),
                                                                          (25, 'CARD'), (25, 'CASH'), (25, 'ACCOUNT'),
                                                                          (26, 'CARD'), (26, 'ACCOUNT'),
                                                                          (27, 'CARD'), (27, 'CASH'),
                                                                          (28, 'CARD'), (28, 'CASH'), (28, 'ACCOUNT'),
                                                                          (29, 'CARD'), (29, 'ACCOUNT'),
                                                                          (30, 'CARD'), (30, 'CASH'), (30, 'ACCOUNT');