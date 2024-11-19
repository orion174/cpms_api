INSERT INTO cpms_local.cpms_company
(company_id, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (1, 'CODEIDEA', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_company
(company_id, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (2, '강남구청', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_company
(company_id, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (3, '중랑구청', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (1, 2, '강남구청 행정포털', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (2, 3, '중랑구청 행정포털', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (3, 3, '중랑구 게시판', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_company
  (company_id, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
  (1, '강남구청', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (1, 'ADMIN', 1, 'N', 'admin', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '관리자', '관리자', 'admin', '01012341234', '관리자', NULL, NULL, '2024-10-06', 1, NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (2, 'USER', 2, 'N', 'orion174', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '스마트행정과', '행정포털 담당자', '박범진', '01012341234', '7급', NULL, NULL, '2024-11-14', 1, NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (3, 'USER', 3, 'N', 'orion175', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '행정지원과', '담당자', '홍길동', '01012341234', '9급', NULL, NULL, '2024-11-14', 1, NULL, NULL);

INSERT INTO cpms_local.com_code_detail
(com_code_id, master_code_id, group_id, code_id, code_nm, depth, sort_order, use_yn)
VALUES
    (1, '10', '', '10', '문의', 1, 1, 'Y');

INSERT INTO cpms_local.com_code_detail
(com_code_id, master_code_id, group_id, code_id, code_nm, depth, sort_order, use_yn)
VALUES
    (2, '10', '', '20', '오류', 1, 2, 'Y');

INSERT INTO cpms_local.com_code_detail
(com_code_id, master_code_id, group_id, code_id, code_nm, depth, sort_order, use_yn)
VALUES
    (3, '20', '', '10', '접수대기', 1, 1, 'Y');

INSERT INTO cpms_local.com_code_detail
(com_code_id, master_code_id, group_id, code_id, code_nm, depth, sort_order, use_yn)
VALUES
    (4, '20', '', '20', '접수완료', 1, 2, 'Y');

INSERT INTO cpms_local.com_code_detail
(com_code_id, master_code_id, group_id, code_id, code_nm, depth, sort_order, use_yn)
VALUES
    (5, '20', '', '30', '처리중', 1, 3, 'Y');

INSERT INTO cpms_local.com_code_detail
(com_code_id, master_code_id, group_id, code_id, code_nm, depth, sort_order, use_yn)
VALUES
    (6, '20', '', '40', '반려', 1, 4, 'Y');

INSERT INTO cpms_local.com_code_detail
(com_code_id, master_code_id, group_id, code_id, code_nm, depth, sort_order, use_yn)
VALUES
    (7, '20', '', '50', '처리완료', 1, 5, 'Y');

