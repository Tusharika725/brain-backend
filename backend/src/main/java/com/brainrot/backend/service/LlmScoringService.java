package com.brainrot.backend.service;

import com.brainrot.backend.dto.CaptionResponseDto;
import com.brainrot.backend.model.User;
import com.brainrot.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Service
public class LlmScoringService {

    private static final Logger logger = LoggerFactory.getLogger(LlmScoringService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public LlmScoringService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CaptionResponseDto gradeCaption(String email, String category, String imageUrl, String caption) {

        CaptionResponseDto aiFeedback = new CaptionResponseDto();
        String safeCategory = category != null ? category.trim() : "";
        String safeImageUrl = imageUrl != null ? imageUrl.trim() : "";
        String safeCaption = caption != null ? caption.replace("\"", "\\\"").replace("\n", " ") : "";
        logger.info("Starting grading process for category: [{}]", safeCategory);

        if (safeCategory.equalsIgnoreCase("Explicit Content")) {
            aiFeedback.setAuraGained(-50);
            aiFeedback.setFeedback("Caught in 4K. -50 Aura.");

        } else if (safeCategory.equalsIgnoreCase("Spiritual Path")) {
            aiFeedback.setAuraGained(50);
            aiFeedback.setFeedback("Neural cooldown successful. +50 Aura.");

        } else {
            // 3. DEFENSE RESTORED: Never call Google with a blank image URL!
            if (safeImageUrl.isEmpty()) {
                logger.error("Missing Image URL for category: [{}]", safeCategory);
                aiFeedback.setAuraGained(0);
                aiFeedback.setFeedback("Error: The frontend forgot to send the image URL!");
            } else {
                String finalUrl = apiUrl + "?key=" + apiKey;

                try {
                    byte[] imageBytes = restTemplate.getForObject(safeImageUrl, byte[].class);
                    String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

                    String rawPrompt = """
                            You are a harsh, Gen-Z comedy judge grading a user's 25-word caption for the attached image.
                            Image Category: %s
                            User's Caption: %s
                            
                            Look at the image and the caption. Grade them strictly on this rubric:
                            - vocabulary: out of 10
                            - grammar: out of 10
                            - creativity: out of 15
                            - relevance: out of 15 (Does the caption actually match what is happening in the picture?)
                            - syntax: out of 10
                            
                            Calculate the 'auraGained' out of 60.
                            Provide a short, savage Gen-Z roast as 'feedback'.
                            
                            You MUST respond ONLY with a valid, flat JSON object. Do not use Markdown formatting or backticks.
                            Example format: {"vocabulary": 5, "grammar": 8, "creativity": 10, "relevance": 12, "syntax": 7, "auraGained": 42, "feedback": "Your grammar is mid and this caption has nothing to do with the picture. Ick."}
                            """.formatted(category, caption);

                    String safePrompt = rawPrompt.replace("\"", "\\\"");
                    String requestBody = """
                            {
                              "contents": [{
                                "parts": [
                                  {"text": "%s"},
                                  {
                                    "inlineData": {
                                      "mimeType": "image/jpeg",
                                      "data": "%s"
                                    }
                                  }
                                ]
                              }]
                            }
                            """.formatted(safePrompt, base64Image);

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

                    logger.info(" Sending image and caption to Gemini API...");
                    ResponseEntity<String> response = restTemplate.postForEntity(finalUrl, request, String.class);

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(response.getBody());
                    String extractedAiJson = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

                    aiFeedback = mapper.readValue(extractedAiJson, CaptionResponseDto.class);
                    logger.info("Gemini scoring complete. Aura gained: {}", aiFeedback.getAuraGained());

                } catch (Exception e) {
                    logger.error("AI Grading Failed! Reason: {}", e);
                    aiFeedback.setFeedback("The neural link crashed. You get 0 points, boomer.");
                    aiFeedback.setAuraGained(0);
                }
            }
        }

        if (email == null || email.trim().isEmpty() || email.trim().equalsIgnoreCase("null")) {
            logger.warn("Score not saved: No email provided by frontend.");
            aiFeedback.setTotalAuraPoints(0);
            return aiFeedback;
        }

        String safeEmail = email.trim().toLowerCase();
        Optional<User> existingUserOpt = userRepository.findByEmail(safeEmail);

        if (existingUserOpt.isPresent()) {
            User user = existingUserOpt.get();
            user.addAuraPoints(aiFeedback.getAuraGained());
            userRepository.save(user);
            aiFeedback.setTotalAuraPoints(user.getAuraPoints());
            logger.info("Saved new score for [{}]. Total Aura: {}", safeEmail, user.getAuraPoints());
        } else {
            logger.warn(" Hacker blocked! Attempted to save score for non-existent email: [{}]", safeEmail);
            aiFeedback.setTotalAuraPoints(0);
        }
        return aiFeedback;
    }
}