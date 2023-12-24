package com.backend.Player;

import java.io.IOException;
import java.util.List;

import com.backend.Users.Users;
import com.backend.Users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Player.Player;
import com.backend.Player.PlayerRepository;

@RestController
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    UsersRepository usersRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/players")
    List<Player> getAllPlayers() throws IOException {
        List<Player> players = playerRepository.findAll();
        //Player.mostMoved(players);
        return playerRepository.findAll();
    }

    @GetMapping(path = "/players/{id}")
    Player getPlayerById(@PathVariable int id) {
        return playerRepository.findById(id);
    }


    @PostMapping(path = "/players")
    String createPlayer(@RequestBody Player player) {

        if (player == null) return failure;

        playerRepository.save(player);
        return success;
    }


    @PutMapping(path = "/players/{id}")
    Player updatePlayer(@PathVariable int id, @RequestBody Player request) {
        Player player = playerRepository.findById(id);
        if (player == null) return null;

        playerRepository.save(request);
        return playerRepository.findById(id);
    }

    /*
    @PutMapping("/player/{playerId}/User/{userId}")
    String assignPlayerToUser(@PathVariable int userId,@PathVariable int playerId){
        Users user = usersRepository.findById(userId);
        Player player = playerRepository.findById(playerId);
        if(user == null || player == null)
            return failure;
        player.setUser(user);
        user.setPlayer(player);
        usersRepository.save(user);
        return success;
    }
    */

    @PutMapping(path ="/players_move/{id}/move{type}")
    String updateMove(@PathVariable int playerId,@PathVariable String moveType){

        return success;
    }

    @DeleteMapping(path = "/players/{id}")
    String deletePlayer(@PathVariable int id) {
        playerRepository.deleteById(id);
        return success;
    }

}
