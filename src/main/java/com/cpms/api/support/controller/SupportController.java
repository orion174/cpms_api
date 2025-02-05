package com.cpms.api.support.controller;

import com.cpms.api.support.dto.req.ReqSupportDTO;
import com.cpms.api.support.dto.req.ReqSupportListDTO;
import com.cpms.api.support.dto.req.ReqSupportResponseDTO;
import com.cpms.api.support.service.SupportService;
import com.cpms.common.res.ApiRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertSupportRequest(
            @RequestPart(value = "supportFile", required = false) MultipartFile[] supportFile,
            @ModelAttribute ReqSupportDTO reqSupportDTO)
            throws Exception {
        if (supportFile != null) {
            reqSupportDTO.setSupportFile(supportFile);
        }

        return new ResponseEntity<> (
            new ApiRes(supportService.insertSupportRequest(reqSupportDTO)), HttpStatus.OK
        );
    }

    @PostMapping("/list")
    public ResponseEntity<?> selectSupportList(@RequestBody ReqSupportListDTO reqSupportListDTO) {
        return new ResponseEntity<> (
            new ApiRes(supportService.selectSupportList(reqSupportListDTO)), HttpStatus.OK
        );
    }

    @PostMapping("/detail")
    public ResponseEntity<?> selectSupportDetail(@RequestBody ReqSupportDTO reqSupportDTO) {
        return new ResponseEntity<> (
            new ApiRes(supportService.selectSupportDetail(reqSupportDTO)), HttpStatus.OK
        );
    }

    @GetMapping("/fileDownload/{suportFileId}")
    public ResponseEntity<?> fileDownload(@PathVariable int suportFileId) {
        return supportService.fileDownload(suportFileId);
    }

    @PostMapping("/fileDelete/{suportFileId}")
    public ResponseEntity<?> fileDelete(@PathVariable int suportFileId) {
        return supportService.fileDelete(suportFileId);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody ReqSupportDTO reqSuportDTO) {
        return supportService.updateStatus(reqSuportDTO);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody ReqSupportDTO reqSuportDTO) {
        return supportService.updateUser(reqSuportDTO);
    }

    @PostMapping("/resInsert")
    public ResponseEntity<?> insertResSuport(
            @RequestPart(value = "resFile", required = false) MultipartFile[] resFile,
            @ModelAttribute ReqSupportResponseDTO reqSuportResDTO)
            throws Exception {
        return supportService.insertResSuport(reqSuportResDTO);
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
