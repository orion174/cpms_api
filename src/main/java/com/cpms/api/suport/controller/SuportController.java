package com.cpms.api.suport.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.api.suport.dto.req.ReqSuportDTO;
import com.cpms.api.suport.service.SuportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/suport")
@RequiredArgsConstructor
public class SuportController {

    private final SuportService suportService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertReqSuport(
            @RequestPart(value = "suportFile", required = false) MultipartFile[] suportFile,
            @ModelAttribute ReqSuportDTO reqSuportDTO)
            throws Exception {

        if (suportFile != null) {
            reqSuportDTO.setSuportFile(suportFile);
        }

        return suportService.insertReqSuport(reqSuportDTO);
    }
}
