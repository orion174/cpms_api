package com.cpms.api.support.service;

import com.cpms.api.support.dto.request.*;
import com.cpms.api.support.dto.response.*;

public interface SupportService {

    ResSupportListDTO selectSupportList(ReqSupportListDTO reqDTO);

    ResSupportViewDTO selectSupportView(Integer supportRequestId);

    void insertSupportRequest(ReqInsertSupportDTO reqDTO);

    void updateSupportStatus(Integer supportRequestId);

    void insertSupportResponse(ReqInsertSupportResponseDTO reqDTO);

    void updateSupportResponse(
            Integer supportRequestId,
            Integer supportResponseId,
            ReqUpdateSupportResponseDTO reqDTO);

    void deleteSupportResponse(Integer supportRequestId);

    ResSupportFileDTO fileDownload(int supportFileId);

    void fileDelete(int supportFileId);
}
