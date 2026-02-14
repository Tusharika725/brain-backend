package com.brainrot.backend.controller;

import com.brainrot.backend.model.Fact;
import com.brainrot.backend.repository.FactRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("/api/facts")
public class FactController {

    private final FactRepository factRepository;
    public FactController(FactRepository factRepository){
        this.factRepository = factRepository;
    }
    @GetMapping("/random")
    public Fact getRandomFact(){
        List<Fact> allFacts = factRepository.findAll();
        if(allFacts.isEmpty()){
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(allFacts.size());
        return allFacts.get(randomIndex);

    }

}
