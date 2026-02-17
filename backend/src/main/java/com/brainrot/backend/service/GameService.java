package com.brainrot.backend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {
    private final List<String> brainRotDictionary = List.of("SKIBIDI", "SIGMA", "RIZZ", "GRINDSET", "DOOMSCROLL",
            "BASED", "GHOSTED", "SUS", "DELULU", "MEWING",
            "CHUGJUG", "CAP", "BET", "YEET", "FRFR", "BUSSIN",
            "SALTY", "CRINGE", "NPC", "GOAT", "FOCUS", "FOCUS", "NEURON", "MEMORY", "CLARITY", "LOGIC", "INSIGHT", "DISCIPLINE", "ATTENTION", "CREATIVE", "AWARENESS", "SCROLL", "SWIPE", "ALGORITHM", "DOPAMINE", "REFRESH", "DISTRACT", "VIRAL", "BRAINROT", "TOUCHGRASS", "DELULU"
    );

    public List generateWordSearch() {
        List<String> shuffledList = new ArrayList<>(brainRotDictionary);
        Collections.shuffle(shuffledList);
        return shuffledList.subList(0, 10);

    }
}
