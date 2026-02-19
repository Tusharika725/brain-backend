package com.brainrot.backend.controller;

import com.brainrot.backend.dto.FactDto;
import com.brainrot.backend.service.FactService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/facts")
public class FactController {

    private final FactService factService;

    public FactController(FactService factService) {
        this.factService = factService;
    }

    @GetMapping("/random")
    public FactDto getRandomFact() {
        return factService.getFactsfromInternet();
    }
}


