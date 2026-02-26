package com.brainrot.backend.service;

import com.brainrot.backend.model.User;
import com.brainrot.backend.repository.UserRepository;
import com.brainrot.backend.util.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private  final UserRepository userRepository;
     public UserService(UserRepository userRepository){
         this.userRepository = userRepository;
     }
     //signup
    public User registerUser(String email , String username , String rawPassword){
         if(email==null || username == null ||rawPassword ==null){
             logger.error("Signup blocked: frontend send null data");
             return null;
         }
         String safeEmail = email.trim().toLowerCase();
         String safeUsername = username.trim();
         if(userRepository.findByEmail(safeEmail).isPresent()){
             logger.warn("signup blocked : Email [{}] is already registered",safeEmail);
             return null;
         }
         String hashedPass = HashUtil.hashPassword(rawPassword);
         User newUser = new User(safeEmail,safeUsername,hashedPass);
        logger.info("Successfully registered new user with email: [{}]", safeEmail);
        return userRepository.save(newUser);
    }
    //login
    public User authenticateUser(String email,String rawPassword){
        if (email == null || rawPassword == null) {
            logger.error("Login blocked: Frontend sent null credentials.");
            return null;
        }
         String safeEmail = email.trim().toLowerCase();
        Optional<User> existingUserOpt = userRepository.findByEmail(safeEmail);
        if (existingUserOpt.isEmpty()) {
            logger.warn("Login failed: Email [{}] not found.", safeEmail);
            return null;
        }
        User user = existingUserOpt.get();
        String hashedAttempt = HashUtil.hashPassword(rawPassword);

        if (user.getPasswordHash().equals(hashedAttempt)) {
            logger.info("Successful login for email: [{}]", safeEmail);
            return user; // Passwords match!
        } else {
            logger.warn("Login failed: Wrong password for [{}]", safeEmail);
            return null; // Wrong password!
        }
    }

}
