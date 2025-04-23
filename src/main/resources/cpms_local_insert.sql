
INSERT INTO cpms_local.cpms_company
(company_id, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (1, '코드아이디어', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_company
(company_id, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (2, 'Heritage', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_company
(company_id, company_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (3, '마켓헤머', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (1, 2, '헤리티지 센트럴', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (2, 2, '헤리티지 청담', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (3, 3, '마켓헤머 Web', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (4, 3, '마켓헤머 App', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (5, 3, '마켓헤머 리뉴얼', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_project
(project_id, company_id, project_nm, reg_id, reg_dt, udt_id, udt_dt, del_yn, del_id, del_dt)
VALUES
    (6, 3, '헤머넷', 1, '2024-11-14', NULL, NULL, 'N', NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (1, 'ADMIN', 1, 'N', 'admin', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '관리자', '관리자', 'admin', '01062588390', '관리자', NULL, NULL, '2024-10-06', 1, NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (2, 'USER', 2, 'N', 'orion174', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '헤리티지', '개발 테스트', '박범진', '01062588390', '팀원', NULL, NULL, '2024-11-14', 1, NULL, NULL);

INSERT INTO cpms_local.cpms_user
(user_id, auth_type, company_id, del_yn, login_id, login_pw, use_yn, user_dept, user_info, user_nm, user_phone, user_pos, del_dt, del_id, reg_dt, reg_id, udt_dt, udt_id)
VALUES
    (3, 'USER', 3, 'N', 'orion175', '$2a$10$0Cqt17WWRQFnM2HEI01Q.O2UoxxAsHo6z.tXUl6D42jjEu0ASZXxe', 'Y', '마켓헤머', '개발 테스트', '박범진', '01062588390', '팀원', NULL, NULL, '2024-11-14', 1, NULL, NULL);