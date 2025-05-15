INSERT INTO cpms_local.common_code
(code_id, group_code, code_nm, depth, sort_order, use_yn)
VALUES
    (1, '10', '문의', 1, 1, 'Y');

INSERT INTO cpms_local.common_code
(code_id, group_code, code_nm, depth, sort_order, use_yn)
VALUES
    (2, '10', '오류', 1, 2, 'Y');

INSERT INTO cpms_local.common_code
(code_id, group_code, code_nm, depth, sort_order, use_yn)
VALUES
    (3, '20', '접수대기', 1, 1, 'Y');

INSERT INTO cpms_local.common_code
(code_id, group_code, code_nm, depth, sort_order, use_yn)
VALUES
    (4, '20', '접수완료', 1, 2, 'Y');

INSERT INTO cpms_local.common_code
(code_id, group_code, code_nm, depth, sort_order, use_yn)
VALUES
    (5, '20', '처리중', 1, 3, 'Y');

INSERT INTO cpms_local.common_code
(code_id, group_code, code_nm, depth, sort_order, use_yn)
VALUES
    (6, '20', '반려', 1, 4, 'Y');

INSERT INTO cpms_local.common_code
(code_id, group_code, code_nm, depth, sort_order, use_yn)
VALUES
    (7, '20', '처리완료', 1, 5, 'Y');

INSERT INTO cpms_local.common_code
(code_id, group_code, code_nm, depth, sort_order, use_yn)
VALUES
    (8, '20', '이슈종료', 1, 6, 'Y');

INSERT INTO cpms_local.cpms_company
(company_id, auth_type, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (1, 'TEMP','임시 사용자', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_company
(company_id, auth_type, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (2, 'ADMIN', '코드아이디어', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_company
(company_id, auth_type, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (3, 'USER', 'Heritage', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_company
(company_id, auth_type, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (4, 'USER', '마켓헤머', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (1, 1, 'CPMS 문의', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (2, 3, '헤리티지 센트럴', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (3, 3, '헤리티지 청담', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (4, 4, '마켓헤머 Web', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (5, 4, '마켓헤머 App', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (6, 4, '마켓헤머 리뉴얼', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (7, 4, '헤머넷', 1, NOW(), NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (1, 'ADMIN', 1, 'N', 'admin', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '관리자', '관리자', 'admin', '01000000000', '관리자', NULL, NULL, NOW(), 1, NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (2, 'USER', 2, 'N', 'orion174', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '헤리티지', '개발 테스트', '박범진', '01000000000', '팀원', NULL, NULL, NOW(), 1, NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (3, 'USER', 3, 'N', 'orion175', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '마켓헤머', '개발 테스트', '박범진', '01000000000', '팀원', NULL, NULL, NOW(), 1, NULL, NULL);