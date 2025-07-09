package com.cpms.api.user.dto.request;

import com.cpms.common.helper.ReqPageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqUserListDTO extends ReqPageDTO {
    private Integer seachCompanyId;

    private String searchAuthType;

    private String searchUseYn;

    private String searchUserNm;
}
