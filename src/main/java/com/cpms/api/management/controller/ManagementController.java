package com.cpms.api.management.controller;

import org.springframework.web.bind.annotation.*;

import com.cpms.api.management.service.ManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/management")
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;
}
