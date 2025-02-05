package com.cpms.api.support.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.api.support.dto.req.*;
import com.cpms.api.support.service.SuportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/support")
public class SupportController {

    private final SuportService suportService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertSupportRequest(
            @RequestPart(value = "supportFile", required = false) MultipartFile[] supportFile,
            @ModelAttribute ReqSupportDTO reqSupportDTO)
            throws Exception {
        if (supportFile != null) {
            reqSupportDTO.setSupportFile(supportFile);
        }

        return suportService.insertSupportRequest(reqSupportDTO);
    }

    @PostMapping("/list")
    public ResponseEntity<?> selectSuportList(@RequestBody ReqSupportListDTO reqSuportListDTO) {
        return suportService.selectSuportList(reqSuportListDTO);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> selectSuportDetail(@RequestBody ReqSupportDTO reqSuportDTO) {
        return suportService.selectSuportDetail(reqSuportDTO);
    }

    @GetMapping("/fileDownload/{suportFileId}")
    public ResponseEntity<?> fileDownload(@PathVariable int suportFileId) {
        return suportService.fileDownload(suportFileId);
    }

    @PostMapping("/fileDelete/{suportFileId}")
    public ResponseEntity<?> fileDelete(@PathVariable int suportFileId) {
        return suportService.fileDelete(suportFileId);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody ReqSupportDTO reqSuportDTO) {
        return suportService.updateStatus(reqSuportDTO);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody ReqSupportDTO reqSuportDTO) {
        return suportService.updateUser(reqSuportDTO);
    }

    @PostMapping("/resInsert")
    public ResponseEntity<?> insertResSuport(
            @RequestPart(value = "resFile", required = false) MultipartFile[] resFile,
            @ModelAttribute ReqSupportResponseDTO reqSuportResDTO)
            throws Exception {
        return suportService.insertResSuport(reqSuportResDTO);
    }

    @PostMapping("/resUpdate")
    public ResponseEntity<?> updateResSuport(
            @RequestPart(value = "resFile", required = false) MultipartFile[] resFile,
            @ModelAttribute ReqSupportResponseDTO reqSuportResDTO)
            throws Exception {
        return suportService.updateResSuport(reqSuportResDTO);
    }

    @PostMapping("/resDelete")
    public ResponseEntity<?> deleteResSuport(@RequestBody ReqSupportDTO reqSuportDTO)
            throws Exception {
        return suportService.deleteResSuport(reqSuportDTO);
    }
}
