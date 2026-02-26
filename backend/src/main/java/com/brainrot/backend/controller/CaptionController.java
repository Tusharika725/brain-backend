package com.brainrot.backend.controller;

import com.brainrot.backend.dto.CaptionImageDto;
import com.brainrot.backend.dto.CaptionResponseDto;
import com.brainrot.backend.service.ImageService;
import com.brainrot.backend.service.LlmScoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/captions")
@CrossOrigin(origins = "*")
public class CaptionController {

    private final ImageService imageService;
    private final LlmScoringService llmScoringService;

    public CaptionController(ImageService imageService, LlmScoringService llmScoringService) {
        this.imageService = imageService;
        this.llmScoringService = llmScoringService;
    }

    @GetMapping("/image")
    public ResponseEntity<CaptionImageDto> getImageByCategory(@RequestParam String category) {

        CaptionImageDto image = imageService.getRandomImageByCategory(category);
        return ResponseEntity.ok(image);

    }

    @PostMapping("/evaluate")
    public ResponseEntity<CaptionResponseDto> evaluateCaption(@RequestParam String email, @RequestParam String category, @RequestParam String caption, @RequestParam String imageUrl) {
        System.out.println("judging caption for image " + imageUrl);
        CaptionResponseDto aiGrade = llmScoringService.gradeCaption(email, category, imageUrl, caption);
        return ResponseEntity.ok(aiGrade);


    }


}
