package com.cpms.api.support.service;

import com.cpms.api.support.dto.request.*;
import com.cpms.api.support.dto.response.*;

public interface SupportService {

    ResSupportListDTO selectSupportList(ReqSupportListDTO reqSupportListDTO);

    ResSupportDetailDTO selectSupportDetail(ReqSupportDTO reqSupportDTO);

    void insertSupportRequest(ReqSupportDTO reqSupportDTO);

    void insertSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO);

    void updateSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO);

    void deleteSupportResponse(ReqSupportDTO reqSupportDTO);

    void updateSupportStatus(ReqSupportDTO reqSupportDTO);

    void updateResponseUserInfo(ReqSupportDTO reqSupportDTO);

    ResSupportFileDTO fileDownload(int supportFileId);

    void fileDelete(int supportFileId);
}
