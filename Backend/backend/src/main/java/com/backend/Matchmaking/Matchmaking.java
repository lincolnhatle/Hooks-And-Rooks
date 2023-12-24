package com.backend.Matchmaking;

import com.backend.Match.Match;
import com.backend.Player.Player;
import com.backend.Users.Users;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

@Entity
public class Matchmaking {

    private static List<Match> openMatchList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    private Queue<Users> matchmakingQueue;

    public Matchmaking() {
        matchmakingQueue = new LinkedList<Users>();
    }

    void addToQueue(Users user) {

        matchmakingQueue.add(user);

    }

    public Users checkMatchmakingQueue() {
        Users otherUser = null;
        try {
            otherUser = matchmakingQueue.remove();
        }
        catch(Throwable NoSuchElementException) {
            return null;
        }
        return otherUser;
    }

    public Match makeNewMatch(Player user1, Player user2) {
        Match newMatch = new Match(user1, user2);

        openMatchList.add(newMatch);

        return newMatch;
    }

    public void endGame(int id) {

    }

}
