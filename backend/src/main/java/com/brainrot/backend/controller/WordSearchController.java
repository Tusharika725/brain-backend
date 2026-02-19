package com.brainrot.backend.controller;

import com.brainrot.backend.model.User;
import com.brainrot.backend.service.UserService;
import com.brainrot.backend.service.WordSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/games")
public class WordSearchController {

    private final WordSearchService wordSearchService;
    public WordSearchController(WordSearchService wordSearchService){
        this.wordSearchService = wordSearchService;
    }
    @GetMapping("/wordsearch/generate")
    public List<String> getWordSearchData(){
        return wordSearchService.generateWordSearch();
    }
    @PostMapping("/{username}/wordsearch")
    public User submitWordSearch(
            @PathVariable String username,
            @RequestParam int wordsFound,
            @RequestParam int gameAttempts) {
        return wordSearchService.processWordSearch(username, wordsFound, gameAttempts);
    }
}
