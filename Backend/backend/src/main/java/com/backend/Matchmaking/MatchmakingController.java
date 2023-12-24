package com.backend.Matchmaking;

import com.backend.Match.Match;
import com.backend.Match.MatchRepository;
import com.backend.Player.Player;
import com.backend.Users.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class MatchmakingController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    MatchRepository matchrepository;

    private static Matchmaking matchmaking = new Matchmaking();

    @PostMapping(path = "/matchmaking")
    Match enterQueue(@RequestBody Users user) {
        Users otherUser = matchmaking.checkMatchmakingQueue();

        if (otherUser != null) {
            Player player1 = new Player(user);
            Player player2 = new Player(otherUser);
            Match newMatch = new Match(player1, player2);

            matchrepository.save(newMatch);

            return newMatch;
        }

        matchmaking.addToQueue(user);

        return null;
    }

    @GetMapping(path = "/matchmaking")
    Match getOpponent(@RequestBody Users user) {
        Users opponent = matchmaking.checkMatchmakingQueue();
        if (opponent != null) {
            Player player1 = new Player(user);
            Player player2 = new Player(opponent);

            Match newMatch = new Match(player1, player2);

            matchrepository.save(newMatch);
        }
        return null;
    }




}
