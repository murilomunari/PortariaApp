package com.murilo.portariaApp.controller;

import com.murilo.portariaApp.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/package")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;


}
