package com.cpms.api.support.service;

import com.cpms.api.support.dto.request.*;
import com.cpms.api.support.dto.response.*;

public interface SupportService {

    ResSupportListDTO selectSupportList(ReqSupportListDTO reqSupportListDTO);

    ResSupportViewDTO selectSupportView(Integer supportRequestId);

    void insertSupportRequest(ReqSupportDTO reqSupportDTO);

    void insertSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO);

    void updateSupportStatus(Integer supportRequestId);

    void updateSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO);

    void deleteSupportResponse(Integer supportRequestId);

    ResSupportFileDTO fileDownload(int supportFileId);

    void fileDelete(int supportFileId);
}
