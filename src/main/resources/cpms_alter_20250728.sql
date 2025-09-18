ALTER TABLE `cpms_company`
    ADD COLUMN `auth_type` varchar(30) DEFAULT NULL COMMENT '업체 권한' AFTER `company_id`,
    ADD COLUMN `zip_code` varchar(255) DEFAULT NULL COMMENT '우편번호' AFTER `company_nm`,
    ADD COLUMN `address` varchar(255) DEFAULT NULL COMMENT '주소' AFTER `zip_code`,
    ADD COLUMN `extra_address` varchar(255) DEFAULT NULL COMMENT '추가 주소' AFTER `address`,
    ADD COLUMN `homepage` varchar(255) DEFAULT NULL COMMENT '홈페이지' AFTER `extra_address`,
    ADD COLUMN `company_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '회사 추가 정보' AFTER `homepage`,
    ADD COLUMN `admin_note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '관리자 메모' AFTER `company_info`,
    ADD COLUMN `use_yn` enum('N','Y') NOT NULL DEFAULT 'Y' COMMENT '사용 여부' AFTER `company_info`;

ALTER TABLE `cpms_project`
    ADD COLUMN `project_info` varchar(255) DEFAULT NULL COMMENT '프로젝트 추가 정보' AFTER `project_nm`,
    ADD COLUMN `progress_yn` enum('N','Y') NOT NULL DEFAULT 'Y' COMMENT '진행 여부' AFTER `project_info`;
