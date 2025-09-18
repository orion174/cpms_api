package com.cpms.api.support.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.api.support.dto.request.ReqSupportDTO;
import com.cpms.api.support.dto.request.ReqSupportListDTO;
import com.cpms.api.support.dto.request.ReqSupportResponseDTO;
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
    public ResponseEntity<ApiResponse> selectSupportList(ReqSupportListDTO reqSupportListDTO) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        supportService.selectSupportList(reqSupportListDTO),
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
            @ModelAttribute ReqSupportDTO reqSupportDTO) {
        supportService.insertSupportRequest(reqSupportDTO);

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
            @ModelAttribute ReqSupportResponseDTO reqSupportResponseDTO) {
        supportService.insertSupportResponse(reqSupportResponseDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.INSERT_SUCCESS.getMessage()));
    }

    @PostMapping("/update-response")
    public ResponseEntity<ApiResponse> updateSupportResponse(
            @RequestPart(value = "responseFile", required = false) MultipartFile[] responseFile,
            @ModelAttribute ReqSupportResponseDTO reqSupportResponseDTO) {
        supportService.updateSupportResponse(reqSupportResponseDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.UPDATE_SUCCESS.getMessage()));
    }

    //    @PutMapping("/update/{id}/response")
    //    public ResponseEntity<ApiResponse> updateSupportResponse(
    //            @PathVariable("id") Integer supportRequestId,
    //            @RequestPart(value = "responseFile", required = false) MultipartFile[]
    // responseFile,
    //            @ModelAttribute ReqSupportResponseDTO reqSupportResponseDTO) {
    //        supportService.updateSupportResponse(reqSupportResponseDTO);
    //
    //        return ResponseEntity.ok(
    //                ApiResponse.success(
    //                        HttpStatus.OK.value(), ResponseMessage.UPDATE_SUCCESS.getMessage()));
    //    }

    @PostMapping("/delete-response")
    public ResponseEntity<ApiResponse> deleteSupportResponse(
            @RequestBody Integer supportRequestId) {
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

    @PostMapping("/file/{supportFileId}/delete")
    public ResponseEntity<ApiResponse> fileDelete(@PathVariable int supportFileId) {
        supportService.fileDelete(supportFileId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.DELETE_SUCCESS.getMessage()));
    }
}
