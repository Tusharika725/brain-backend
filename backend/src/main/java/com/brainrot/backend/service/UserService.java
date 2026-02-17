package com.brainrot.backend.service;

import com.brainrot.backend.model.User;
import com.brainrot.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private  final UserRepository userRepository;
     public UserService(UserRepository userRepository){
         this.userRepository = userRepository;
     }

     public User processWordSearch(String username, int wordsFound , int gameAttempts){
         if(wordsFound < 0 || wordsFound>10){
             throw new IllegalCallerException("Cheater detected! You can only find between 0 and 10 words.");
         }
         if (gameAttempts < 1 || gameAttempts > 3) {
             throw new IllegalArgumentException("Invalid attempts! You get a maximum of 3 tries.");
         }
         //
         Optional<User> existingUser = userRepository.findById(username);
         User user = existingUser.orElseGet(() -> new User(username));
         int earnedPoints = wordsFound*5;
         if(gameAttempts>1){
             int penalty = (gameAttempts-1)*5;
             earnedPoints = earnedPoints-penalty;
         }
         if(earnedPoints<0){
             earnedPoints=0;
         }
         user.setWordSearchScore(earnedPoints);
         return userRepository.save(user);
     }


}
