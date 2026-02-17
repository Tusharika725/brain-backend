package com.brainrot.backend.service;

import com.brainrot.backend.model.FactDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FactService {
    public FactDto getFactsfromInternet() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://uselessfacts.jsph.pl/api/v2/facts/random";


        return restTemplate.getForObject(url, FactDto.class);
    }
}
