CREATE TABLE `cpms_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_type` varchar(255) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `del_yn` enum('Y','N') DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  `login_pw` varchar(255) DEFAULT NULL,
  `use_yn` enum('Y','N') DEFAULT NULL,
  `user_dept` varchar(255) DEFAULT NULL,
  `user_info` varchar(255) DEFAULT NULL,
  `user_nm` varchar(255) DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `user_pos` varchar(255) DEFAULT NULL,
  `del_dt` datetime(6) DEFAULT NULL,
  `del_id` int(10) unsigned DEFAULT NULL,
  `reg_dt` datetime(6) DEFAULT NULL,
  `reg_id` int(10) unsigned DEFAULT NULL,
  `udt_dt` datetime(6) DEFAULT NULL,
  `udt_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE `user_login_history` (
  `login_history_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `access_ip` varchar(255) DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `reg_dt` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`login_history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='사용자 로그인 이력';

CREATE TABLE `com_code_master` (
  `master_code_id` varchar(10) NOT NULL COMMENT '공통 코드 마스터 id',
  `master_code_nm` varchar(255) DEFAULT NULL COMMENT '공통 코드 마스터 명',
  `req_yn` enum('Y','N') NOT NULL COMMENT '필수 여부',
  `use_yn` enum('Y','N') NOT NULL COMMENT '사용 유무',
  `reg_id` int(10) unsigned DEFAULT NULL COMMENT '등록자',
  `reg_dt` datetime DEFAULT current_timestamp() COMMENT '등록일자',
  `udt_id` int(10) unsigned DEFAULT NULL COMMENT '수정자',
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일자',
  `del_yn` enum('Y','N') NOT NULL COMMENT '삭제 여부',
  `del_id` int(10) unsigned DEFAULT NULL COMMENT '삭제자',
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일자',
  PRIMARY KEY (`master_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='공통 코드 마스터';

CREATE TABLE `com_code_detail` (
  `com_code_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '공통 코드 기본키',
  `master_code_id` varchar(10) NOT NULL COMMENT '공통 코드 마스터 키',
  `group_id` varchar(10) DEFAULT NULL COMMENT '공통 코드 그룹 키',
  `code_id` varchar(10) NOT NULL COMMENT '공통 코드 키',
  `code_nm` varchar(30) DEFAULT NULL COMMENT '공통 코드 명',
  `depth` int(10) unsigned NOT NULL COMMENT '계층 레벨',
  `sort_order` int(10) unsigned NOT NULL COMMENT '정렬 순서',
  `use_yn` enum('Y','N') DEFAULT 'N' COMMENT '사용 유무',
  `reg_id` int(10) unsigned DEFAULT NULL COMMENT '등록자',
  `reg_dt` datetime DEFAULT current_timestamp() COMMENT '등록일자',
  `udt_id` int(10) unsigned DEFAULT NULL COMMENT '수정자',
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일자',
  `del_yn` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `del_id` int(10) unsigned DEFAULT NULL COMMENT '삭제자',
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일자',
  PRIMARY KEY (`com_code_id`),
  KEY `idx_master_code_id` (`master_code_id`),
  KEY `idx_code_id` (`code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='공통 코드 상세';

CREATE TABLE `suport_req` (
  `suport_req_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `req_company_id` int(10) unsigned NOT NULL COMMENT '요청 회사 키',
  `user_company_id` int(10) unsigned NOT NULL COMMENT '사용자 회사 키',
  `req_project_id` int(10) unsigned NOT NULL COMMENT '프로젝트 키',
  `res_user_id` int(10) unsigned DEFAULT NULL COMMENT '처리 담당자',
  `request_cd` varchar(10) DEFAULT NULL COMMENT '요청 유형 코드',
  `status_cd` varchar(10) DEFAULT NULL COMMENT '처리 상태 코드',
  `req_date` date DEFAULT NULL,
  `res_date` date DEFAULT NULL,
  `suport_title` varchar(255) DEFAULT NULL COMMENT '문의 제목',
  `suport_editor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci DEFAULT NULL COMMENT '문의 상세',
  `reg_id` int(11) unsigned DEFAULT NULL COMMENT '생성자',
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일',
  `udt_id` int(11) unsigned DEFAULT NULL COMMENT '수정자',
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일',
  `del_yn` enum('N','Y') CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `del_id` int(11) unsigned DEFAULT NULL COMMENT '삭제자',
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일',
  PRIMARY KEY (`suport_req_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='유지보수 문의 정보';

CREATE TABLE `suport_file` (
  `suport_file_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '기본키',
  `suport_req_id` int(10) unsigned DEFAULT NULL COMMENT '유지보수 요청 키',
  `file_path` varchar(100) DEFAULT NULL COMMENT '파일 경로',
  `file_nm` varchar(100) DEFAULT NULL COMMENT '파일 변환 명',
  `file_og_nm` varchar(100) DEFAULT NULL COMMENT '파일 실제 명',
  `file_ext` varchar(20) DEFAULT NULL COMMENT '파일 확장자',
  `file_size` bigint(20) DEFAULT NULL,
  `reg_id` int(11) NOT NULL,
  `red_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일',
  `udt_id` int(11) DEFAULT NULL,
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일',
  `del_yn` enum('Y','N') NOT NULL,
  `del_id` int(11) DEFAULT NULL,
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일',
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp(),
  `file_category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`suport_file_id`),
  KEY `FK8l7bdyjr5h4rbcpai6rmu6n3r` (`suport_req_id`),
  CONSTRAINT `FK8l7bdyjr5h4rbcpai6rmu6n3r` FOREIGN KEY (`suport_req_id`) REFERENCES `suport_req` (`suport_req_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='유지보수 첨부 파일';

