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
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;
import com.cpms.common.util.FileUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> selectSupportList(
            @RequestBody ReqSupportListDTO reqSupportListDTO) {
        Object result = supportService.selectSupportList(reqSupportListDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @PostMapping("/view")
    public ResponseEntity<ApiResponse> selectSupportView(@RequestBody ReqSupportDTO reqSupportDTO) {
        Object result = supportService.selectSupportView(reqSupportDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse> insertSupportRequest(
            @RequestPart(value = "supportFile", required = false) MultipartFile[] supportFile,
            @ModelAttribute ReqSupportDTO reqSupportDTO) {

        if (supportFile != null) {
            reqSupportDTO.setSupportFile(supportFile);
        }

        supportService.insertSupportRequest(reqSupportDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.INSERT_SUCCESS.getMessage()));
    }

    @PostMapping("/insert-response")
    public ResponseEntity<ApiResponse> insertSupportResponse(
            @RequestPart(value = "responseFile", required = false) MultipartFile[] responseFile,
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

    @PostMapping("/delete-response")
    public ResponseEntity<ApiResponse> deleteSupportResponse(
            @RequestBody ReqSupportDTO reqSupportDTO) {
        supportService.deleteSupportResponse(reqSupportDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.DELETE_SUCCESS.getMessage()));
    }

    @PostMapping("/update-status")
    public ResponseEntity<ApiResponse> updateSupportStatus(
            @RequestBody ReqSupportDTO reqSupportDTO) {
        supportService.updateSupportStatus(reqSupportDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.UPDATE_SUCCESS.getMessage()));
    }

    @PostMapping("/update-user")
    public ResponseEntity<ApiResponse> updateResponseUserInfo(
            @RequestBody ReqSupportDTO reqSupportDTO) {
        supportService.updateResponseUserInfo(reqSupportDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.UPDATE_SUCCESS.getMessage()));
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
