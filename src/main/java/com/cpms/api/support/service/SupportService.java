package com.cpms.api.support.service;

import com.cpms.api.support.dto.request.*;
import com.cpms.api.support.dto.response.*;

public interface SupportService {

    ResSupportListDTO selectSupportList(ReqSupportListDTO reqSupportListDTO);

    ResSupportDetailDTO selectSupportDetail(ReqSupportDTO reqSupportDTO);

    boolean insertSupportRequest(ReqSupportDTO reqSupportDTO);

    boolean insertSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO);

    boolean updateSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO);

    boolean deleteSupportResponse(ReqSupportDTO reqSupportDTO);

    boolean updateSupportStatus(ReqSupportDTO reqSupportDTO);

    boolean updateResponseUserInfo(ReqSupportDTO reqSupportDTO);

    ResSupportFileDTO fileDownload(int supportFileId);

    void fileDelete(int supportFileId);
}
