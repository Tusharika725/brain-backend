package com.brainrot.backend.service;

import com.brainrot.backend.model.User;
import com.brainrot.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WordSearchService {
    public static final String GAME_NAME = "WORD_SEARCH";
    public static final int MAX_POINTS = 10;
    private final UserRepository userRepository;
    private final List<String> brainRotDictionary = List.of("SKIBIDI", "SIGMA", "RIZZ", "GRINDSET", "DOOMSCROLL",
            "BASED", "GHOSTED", "SUS", "DELULU", "MEWING",
            "CHUGJUG", "CAP", "BET", "YEET", "FRFR", "BUSSIN",
            "SALTY", "CRINGE", "NPC", "GOAT", "FOCUS", "FOCUS", "NEURON", "MEMORY", "CLARITY", "LOGIC", "INSIGHT", "DISCIPLINE", "ATTENTION", "CREATIVE", "AWARENESS", "SCROLL", "SWIPE", "ALGORITHM", "DOPAMINE", "REFRESH", "DISTRACT", "VIRAL", "BRAINROT", "TOUCHGRASS", "DELULU"
    );

    public WordSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User processWordSearch(String username, int wordsFound, int gameAttempts) {
        if (wordsFound < 0 || wordsFound > 10) {
            throw new IllegalCallerException("Cheater detected! You can only find between 0 and 10 words.");
        }
        if (gameAttempts < 1 || gameAttempts > 3) {
            throw new IllegalArgumentException("Invalid attempts! You get a maximum of 3 tries.");
        }
        //
        Optional<User> existingUser = userRepository.findbyUsername(username);
        User user = existingUser.orElseGet(() -> new User(username));
        int earnedPoints = wordsFound;
        if (gameAttempts > 1) {
            int penalty = (gameAttempts - 1);
            earnedPoints = earnedPoints - penalty;
        }
        if (earnedPoints < 0) {
            earnedPoints = 0;
        }
        user.saveGameScore(GAME_NAME, earnedPoints, MAX_POINTS);
        return userRepository.save(user);
    }

    public List generateWordSearch() {
        List<String> shuffledList = new ArrayList<>(brainRotDictionary);
        Collections.shuffle(shuffledList);
        return shuffledList.subList(0, 10);

    }
}

