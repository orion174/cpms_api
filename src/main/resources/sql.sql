CREATE TABLE `com_code_master` (
  `master_code_id` varchar(10) NOT NULL COMMENT '공통 코드 마스터 id',
  `master_code_nm` varchar(255) DEFAULT NULL COMMENT '공통 코드 마스터 명',
  `req_yn` enum('Y','N') DEFAULT NULL COMMENT '필수 여부',
  `use_yn` enum('Y','N') DEFAULT NULL COMMENT '사용 유무',
  `reg_id` int(11) DEFAULT NULL COMMENT '등록자',
  `reg_dt` datetime DEFAULT current_timestamp() COMMENT '등록일자',
  `udt_id` int(11) DEFAULT NULL COMMENT '수정자',
  `udt_dt` datetime DEFAULT NULL COMMENT '수정일자',
  `del_yn` enum('Y','N') NOT NULL COMMENT '삭제 여부',
  `del_id` int(11) DEFAULT NULL COMMENT '삭제자',
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

