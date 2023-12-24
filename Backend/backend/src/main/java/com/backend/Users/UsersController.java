package com.backend.Users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.backend.Player.Player;
import com.backend.Player.PlayerRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Users.Users;
import com.backend.Users.UsersRepository;

@RestController
public class UsersController {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    PlayerRepository playerRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    List<Users> getAllUsers(){

        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    Users getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @PostMapping(path = "/users")
    String createUser(@RequestBody Users user) {
        if (user == null) return failure;
        userRepository.save(user);
        return success;
    }
    
    @PutMapping("/users/{id}")
    Users updateUser(@PathVariable int id, @RequestBody Users request) {
        Users user = userRepository.findById(id);
        if(user == null) return null;
        userRepository.save(request);
        return userRepository.findById(id);
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return success;
    }

    @PutMapping(path = "/users/friendRequest/{id}")
    String friendRequest(@PathVariable int id, @RequestBody Users requestingUser) {
        Users otherUser = userRepository.findById(id);
        if(otherUser == null) return failure;
        otherUser.addFriend(requestingUser);

        requestingUser.addFriend(otherUser);
        userRepository.save(otherUser);
        userRepository.save(requestingUser);

        return success;
    }

    @GetMapping(path = "/leaderboard")
    List<Users> getLeaderboard() {
        int i;

        List<Users> userList = new ArrayList<Users>();
        userList = userRepository.findAll();

        Collections.sort(userList, new Comparator<Users>(){
            public int compare(Users o1, Users o2){
                return o2.getWins() - o1.getWins();
            }
        });

        return userList;
    }
}