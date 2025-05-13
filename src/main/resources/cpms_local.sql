CREATE TABLE `common_code` (
  `code_id` int(11) NOT NULL COMMENT '공통 코드 아이디',
  `group_code` varchar(10) NOT NULL COMMENT '공통 코드 그룹',
  `code_nm` varchar(30) DEFAULT NULL COMMENT '공통 코드 명',
  `depth` int(10) unsigned NOT NULL COMMENT '공콩 코드 계층 분류',
  `sort_order` int(10) unsigned NOT NULL COMMENT '코드 정렬 순서',
  `use_yn` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '사용 유무',
  PRIMARY KEY (`com_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='공통 코드';

CREATE TABLE `cpms_company` (
  `company_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `company_nm` varchar(255) DEFAULT NULL COMMENT '회사 명',
  `reg_id` int(11) unsigned DEFAULT NULL COMMENT '생성자',
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일',
  `udt_id` int(11) unsigned DEFAULT NULL COMMENT '수정자',
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일',
  `del_yn` enum('N','Y') NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `del_id` int(11) unsigned DEFAULT NULL COMMENT '삭제자',
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='회사 정보';

CREATE TABLE `cpms_project` (
  `project_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `company_id` int(10) unsigned NOT NULL COMMENT '회사 키',
  `project_nm` varchar(255) DEFAULT NULL COMMENT '프로젝트 명',
  `reg_id` int(11) unsigned DEFAULT NULL COMMENT '생성자',
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일',
  `udt_id` int(11) unsigned DEFAULT NULL COMMENT '수정자',
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일',
  `del_yn` enum('N','Y') NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `del_id` int(11) unsigned DEFAULT NULL COMMENT '삭제자',
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='프로젝트 정보';

CREATE TABLE `cpms_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `auth_type` varchar(255) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  `login_pw` varchar(255) DEFAULT NULL,
  `use_yn` enum('Y','N') CHARACTER SET utf8mb4 DEFAULT NULL,
  `user_dept` varchar(255) DEFAULT NULL,
  `user_info` varchar(255) DEFAULT NULL,
  `user_nm` varchar(255) DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `user_pos` varchar(255) DEFAULT NULL,
  `reg_id` int(10) unsigned DEFAULT NULL,
  `reg_dt` datetime DEFAULT NULL,
  `udt_id` int(10) unsigned DEFAULT NULL,
  `udt_dt` datetime DEFAULT NULL,
  `del_yn` enum('Y','N') DEFAULT NULL,
  `del_id` int(10) unsigned DEFAULT NULL,
  `del_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='사용자 정보';

CREATE TABLE `user_login_history` (
  `login_history_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `access_ip` varchar(255) DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `reg_dt` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`login_history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='사용자 로그인 이력';

CREATE TABLE `support_request` (
  `support_request_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `request_company_id` int(10) unsigned NOT NULL COMMENT '해당 회사 ID의 업체에게 문의를 요청',
  `user_company_id` int(10) unsigned NOT NULL COMMENT '문의를 등록한 사용자의 회사 ID',
  `request_project_id` int(10) unsigned NOT NULL COMMENT '프로젝트 ID',
  `response_user_id` int(10) unsigned DEFAULT 0 COMMENT '해당 문의에 응답한 사용자 ID',
  `request_cd` int(10) unsigned DEFAULT NULL COMMENT '요청 유형 분류 코드',
  `status_cd` int(10) unsigned DEFAULT NULL COMMENT '처리 상태 분류 코드',
  `request_date` date DEFAULT NULL COMMENT '요청 일자',
  `response_date` date DEFAULT NULL COMMENT '응답 일자',
  `support_title` varchar(255) DEFAULT NULL COMMENT '문의 제목',
  `support_editor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci DEFAULT NULL COMMENT '문의 상세 내용',
  `reg_id` int(10) unsigned DEFAULT NULL,
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일',
  `udt_id` int(11) unsigned DEFAULT NULL COMMENT '수정자',
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일',
  `del_yn` enum('N','Y') CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `del_id` int(11) unsigned DEFAULT NULL COMMENT '삭제자',
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일',
  PRIMARY KEY (`support_request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='유지보수 문의 정보';

CREATE TABLE `support_response` (
  `support_response_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `support_request_id` int(10) unsigned NOT NULL COMMENT '유지보수 문의 정보 키',
  `response_title` varchar(255) DEFAULT NULL COMMENT '답변 제목',
  `response_editor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci DEFAULT NULL COMMENT '응답 상세',
  `reg_id` int(10) unsigned DEFAULT NULL,
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일',
  `udt_id` int(11) unsigned DEFAULT NULL COMMENT '수정자',
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일',
  `del_yn` enum('N','Y') CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `del_id` int(11) unsigned DEFAULT NULL COMMENT '삭제자',
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일',
  PRIMARY KEY (`support_response_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='유지보수 응답 정보';

CREATE TABLE `support_file` (
  `support_file_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '기본키',
  `support_request_id` int(10) unsigned DEFAULT NULL COMMENT '유지보수 요청 키',
  `file_type` varchar(10) DEFAULT NULL COMMENT '파일 구분',
  `file_path` varchar(100) DEFAULT NULL COMMENT '파일 물리 경로',
  `file_nm` varchar(100) DEFAULT NULL COMMENT '변환된 파일 명',
  `file_og_nm` varchar(100) DEFAULT NULL COMMENT '실제 파일 명',
  `file_ext` varchar(20) DEFAULT NULL COMMENT '파일 확장자',
  `file_size` bigint(20) DEFAULT NULL COMMENT '파일 크기',
  `reg_id` int(10) unsigned DEFAULT NULL,
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp(),
  `udt_id` int(10) unsigned DEFAULT NULL,
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일',
  `del_yn` enum('Y','N') NOT NULL,
  `del_id` int(10) unsigned DEFAULT NULL,
  `del_dt` datetime DEFAULT NULL COMMENT '삭제일',
  PRIMARY KEY (`support_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='유지보수 첨부 파일';

