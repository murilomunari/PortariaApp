package com.murilo.portariaApp.service;

import com.murilo.portariaApp.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
}
