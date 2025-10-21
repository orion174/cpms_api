package com.cpms.api.support.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpms.api.support.dto.request.ReqInsertSupportDTO;
import com.cpms.api.support.dto.request.ReqInsertSupportResponseDTO;
import com.cpms.api.support.dto.request.ReqSupportListDTO;
import com.cpms.api.support.dto.request.ReqUpdateSupportResponseDTO;
import com.cpms.api.support.dto.response.ResSupportFileDTO;
import com.cpms.api.support.service.SupportService;
import com.cpms.cmmn.response.ApiResponse;
import com.cpms.cmmn.response.ResponseMessage;
import com.cpms.cmmn.util.FileUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> selectSupportList(ReqSupportListDTO reqDTO) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        supportService.selectSupportList(reqDTO),
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @GetMapping("/view")
    public ResponseEntity<ApiResponse> selectSupportView(Integer supportRequestId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        supportService.selectSupportView(supportRequestId),
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse> insertSupportRequest(
            @ModelAttribute ReqInsertSupportDTO reqDTO) {
        supportService.insertSupportRequest(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.INSERT_SUCCESS.getMessage()));
    }

    @PatchMapping("/update/{id}/status")
    public ResponseEntity<ApiResponse> updateSupportStatus(
            @PathVariable("id") Integer supportRequestId) {
        supportService.updateSupportStatus(supportRequestId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.UPDATE_SUCCESS.getMessage()));
    }

    @PostMapping("/insert-response")
    public ResponseEntity<ApiResponse> insertSupportResponse(
            @ModelAttribute ReqInsertSupportResponseDTO reqDTO) {
        supportService.insertSupportResponse(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.INSERT_SUCCESS.getMessage()));
    }

    @PutMapping("/update/{requestId}/response/{responseId}")
    public ResponseEntity<ApiResponse> updateSupportResponse(
            @PathVariable("requestId") Integer supportRequestId,
            @PathVariable("responseId") Integer supportResponseId,
            @ModelAttribute ReqUpdateSupportResponseDTO reqDTO) {
        supportService.updateSupportResponse(supportRequestId, supportResponseId, reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.UPDATE_SUCCESS.getMessage()));
    }

    @PatchMapping("/delete/{requestId}/response")
    public ResponseEntity<ApiResponse> deleteSupportResponse(
            @PathVariable("requestId") Integer supportRequestId) {
        supportService.deleteSupportResponse(supportRequestId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.DELETE_SUCCESS.getMessage()));
    }

    @GetMapping("/file/{supportFileId}/download")
    public ResponseEntity<Resource> fileDownload(@PathVariable int supportFileId) {
        ResSupportFileDTO dto = supportService.fileDownload(supportFileId);

        return FileUtil.fileDownload(dto.getFilePath(), dto.getFileNm());
    }

    @PatchMapping("/file/{supportFileId}/delete")
    public ResponseEntity<ApiResponse> fileDelete(@PathVariable int supportFileId) {
        supportService.fileDelete(supportFileId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.DELETE_SUCCESS.getMessage()));
    }
}
