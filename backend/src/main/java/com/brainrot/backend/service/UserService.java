package com.brainrot.backend.service;

import com.brainrot.backend.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    private static final List<User> users = new ArrayList<>();

    public User createUser(User user){
        users.add(user);
        return user;
    }
    public static List<User> getAllUsers(){
        return users;
    }
}
