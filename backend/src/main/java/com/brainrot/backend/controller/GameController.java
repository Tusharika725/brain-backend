package com.brainrot.backend.controller;

import com.brainrot.backend.service.GameService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    public GameController(GameService gameService){
        this.gameService = gameService;
    }
    @GetMapping("/wordsearch/generate")
    public List<String> getWordSearchData(){
        return gameService.generateWordSearch();
    }
}
