package com.brainrot.backend.controller;

import com.brainrot.backend.dto.CaptionImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/captions")
@CrossOrigin
public class CaptionController {
    @GetMapping("/image")
    public ResponseEntity<CaptionImageDto> getImageByCategory(@RequestParam String category){
        String imageUrl = "";
        String imageId = "";
        switch (category.toLowerCase()) {
            case "meme culture":
                imageId = "meme_01";
                imageUrl = "https://i.imgflip.com/1g8my4.jpg"; // Disaster Girl meme
                break;
            case "political humour":
                imageId = "pol_01";
                imageUrl = "https://i.imgflip.com/4t0m5.jpg"; // Bernie Sanders Mittens
                break;
            case "nature & wild":
                imageId = "nature_01";
                imageUrl = "https://i.imgflip.com/22bdq6.jpg"; // Is this a pigeon?
                break;
            default:
                imageId = "default_01";
                imageUrl = "https://i.imgflip.com/30b1gx.jpg"; // Cat meme fallback
        }

        return ResponseEntity.ok(new CaptionImageDto(imageId, imageUrl));
    }
}
