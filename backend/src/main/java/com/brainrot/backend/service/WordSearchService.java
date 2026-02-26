package com.brainrot.backend.service;

import com.brainrot.backend.dto.WordSearchResponseDto;
import com.brainrot.backend.model.User;
import com.brainrot.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WordSearchService {

    private static final Logger logger = LoggerFactory.getLogger(WordSearchService.class);
    private final UserRepository userRepository;
    private final List<String> auraDictionary = List.of(
            "SKIBIDI", "SIGMA", "RIZZ", "GRINDSET", "DOOMSCROLL",
            "BASED", "GHOSTED", "SUS", "DELULU", "MEWING",
            "CHUGJUG", "CAP", "BET", "YEET", "FRFR", "BUSSIN",
            "SALTY", "CRINGE", "NPC", "GOAT", "FOCUS", "NEURON",
            "MEMORY", "CLARITY", "LOGIC", "INSIGHT", "DISCIPLINE",
            "ATTENTION", "CREATIVE", "AWARENESS", "SCROLL", "SWIPE",
            "ALGORITHM", "DOPAMINE", "REFRESH", "DISTRACT", "VIRAL",
            "BRAINROT", "TOUCHGRASS", "BACKEND", "COMPILE","DEBUG",
            "DEPLOY","YAPPER","BOOMER","VIBECHECK","AURA","UPSCROLL",
            "DETOX","OFFLINE","GROUNDED","BREATHE","MINDFUL","UNPLUG",
            "MERGE","CACHE","REACT","SPRING","FRONTEND","GIGACHAD"
    );

    public WordSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public WordSearchResponseDto processWordSearch(String email, int wordsFound, int gameAttempts) {

        logger.info("Processing Word Search score for email: [{}]", email);

        if (wordsFound < 0 || wordsFound > 10) {
            logger.error("Cheater detected! Invalid word count: {}", wordsFound);
            throw new IllegalArgumentException("Cheater detected! You can only find between 0 and 10 words.");
        }
        if (gameAttempts < 1 || gameAttempts > 3) {
            logger.error("Cheater detected! Invalid game attempts: {}", gameAttempts);
            throw new IllegalArgumentException("Invalid attempts! You get a maximum of 3 tries.");
        }

        int earnedPoints = wordsFound;
        if (gameAttempts > 1) {
            earnedPoints = earnedPoints - (gameAttempts - 1); // Deduct points for extra tries
        }
        earnedPoints = Math.max(earnedPoints, 0); // Math.max ensures the score doesn't drop below 0

        if (email == null || email.trim().isEmpty() || email.trim().equalsIgnoreCase("null")) {
            logger.warn("Score not saved: Frontend did not send an email.");
            return new WordSearchResponseDto(earnedPoints, 0);
        }

        String safeEmail = email.trim().toLowerCase();
        Optional<User> existingUserOpt = userRepository.findByEmail(safeEmail);

        if (existingUserOpt.isEmpty()) {
            logger.warn("Score rejected: No account found for email [{}]", safeEmail);
            return new WordSearchResponseDto(earnedPoints, 0);
        }

        User user = existingUserOpt.get();
        user.addAuraPoints(earnedPoints);
        userRepository.save(user);
        logger.info("Saved Word Search score! Awarded {} points. Total Aura: {}", earnedPoints, user.getAuraPoints());

        return new WordSearchResponseDto(earnedPoints, user.getAuraPoints());
    }

    public List<String> generateWordSearch() {
        List<String> shuffledList = new ArrayList<>(auraDictionary);
        Collections.shuffle(shuffledList);
        logger.info("Generated new 10-word puzzle for the frontend.");
        return new ArrayList(shuffledList.subList(0, 10));
    }
}