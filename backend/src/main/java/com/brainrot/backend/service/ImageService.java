package com.brainrot.backend.service;

import com.brainrot.backend.dto.CaptionImageDto;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private Map<String,List<CaptionImageDto>> imageDatabase = new HashMap<>();
    private final Random  random = new Random();
    private final ObjectMapper objectMapper;
    public ImageService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    @PostConstruct
    public void loadImagesFromJson(){
        try{
            InputStream inputStream = new ClassPathResource("images.json").getInputStream();
            TypeReference<Map<String,List<CaptionImageDto>>> typeref = new TypeReference<>(){};
            imageDatabase = objectMapper.readValue(inputStream,typeref);
            logger.info("Successfully loaded [{}] categories into the Image Database.", imageDatabase.size());
        }
        catch (IOException e){
            logger.error(" FATAL: Could not load images.json from resources!", e);
        }
    }
    public CaptionImageDto getRandomImageByCategory(String category) {
        if (category == null) {
            logger.warn("Frontend sent a null category. Returning default image.");
            return new CaptionImageDto("default_01", "https://i.imgflip.com/30b1gx.jpg"); // Default Drake Meme
        }
        String safeCategory = category.toLowerCase();
        if (imageDatabase.containsKey(safeCategory) && !imageDatabase.get(safeCategory).isEmpty()) {
            List<CaptionImageDto> images = imageDatabase.get(safeCategory);
            int randomIndex = ThreadLocalRandom.current().nextInt(images.size());
            return images.get(randomIndex);
        }

        logger.warn("Category [{}] not found or empty. Returning default fallback image.", safeCategory);
        return new CaptionImageDto("default_01", "https://i.imgflip.com/30b1gx.jpg");
    }
}