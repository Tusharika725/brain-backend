package com.brainrot.backend.controller;

import com.brainrot.backend.dto.WordSearchResponseDto;
import com.brainrot.backend.service.WordSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/games")
public class WordSearchController {

    private static final Logger logger = LoggerFactory.getLogger(WordSearchController.class);
    private final WordSearchService wordSearchService;

    public WordSearchController(WordSearchService wordSearchService) {
        this.wordSearchService = wordSearchService;
    }

    @GetMapping("/wordsearch/generate")
    public ResponseEntity<List<String>> getWordSearchData() {
        logger.info("Frontend requested new word search puzzle");
        List<String> words = wordSearchService.generateWordSearch();
        return ResponseEntity.ok(words);
    }

    @PostMapping("/wordsearch/submit")
    public ResponseEntity<WordSearchResponseDto> submitWordSearch(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam int wordsFound,
            @RequestParam int gameAttempts) {
        logger.info("Incoming Word Search submission for user: [{}]", email);
        WordSearchResponseDto gameStats = wordSearchService.processWordSearch(email, wordsFound, gameAttempts);
        logger.info("Sending updated stats back to frontend for user: [{}]", email);
        return ResponseEntity.ok(gameStats);
    }
}
