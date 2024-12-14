INSERT INTO badge (badge_name, badge_type, badge_color, badge_content, badge_image_url, created_at, updated_at) VALUES

-- 시저 타입 뱃지
('2024년', 'SCISSORS', 'GOLD', '2024년 골드 시저 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/gold_scissors.png', NOW(), NOW()),
('2024년', 'SCISSORS', 'SILVER', '2024년 실버 시저 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/silver_scissors.png', NOW(), NOW()),
('2024년', 'SCISSORS', 'BRONZE', '2024년 브론즈 시저 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/blonze_scissors.png', NOW(), NOW()),

-- 사업자 등록 인증 뱃지
('사업자 등록 인증', 'GENERAL', 'GREEN', '사업자 등록을 인증한 사용자를 위한 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/business_verified.png', NOW(), NOW()),

-- 강아지 전문가 뱃지 (타입: NONE, 색상: BLUE)
('말티즈 전문가', 'GENERAL', 'BLUE', '말티즈 전문가를 위한 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/dog.png', NOW(), NOW()),
('푸들 전문가', 'GENERAL', 'BLUE', '푸들 전문가를 위한 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/dog.png', NOW(), NOW()),
('치와와 전문가', 'GENERAL', 'BLUE', '치와와 전문가를 위한 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/dog.png', NOW(), NOW()),
('포메라니안 전문가', 'GENERAL', 'BLUE', '포메라니안 전문가를 위한 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/dog.png', NOW(), NOW()),
('슈나우저 전문가', 'GENERAL', 'BLUE', '슈나우저 전문가를 위한 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/dog.png', NOW(), NOW()),
('요크셔 테리어 전문가', 'GENERAL', 'BLUE', '요크셔 테리어 전문가를 위한 뱃지', 'https://peauty.s3.ap-northeast-2.amazonaws.com/images/dog.png', NOW(), NOW());
