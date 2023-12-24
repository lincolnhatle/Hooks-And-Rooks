package com.backend.Match;

import com.backend.Player.Player;
import jakarta.persistence.*;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Player player1;

    @OneToOne
    private Player player2;

    private boolean p1Win;
    private boolean p2Win;

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        p1Win = false;
        p2Win = false;
    }

    public Match() {
    }

    public int getId() {
        return id;
    }

    public boolean isP2Win() {
        return p2Win;
    }

    public void setP2Win(boolean p2Win) {
        this.p2Win = p2Win;
    }

    public boolean isP1Win() {
        return p1Win;
    }

    public void setP1Win(boolean p1Win) {
        this.p1Win = p1Win;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
}
