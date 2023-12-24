package com.backend.Move;

import jakarta.persistence.*;

@Entity
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String player;
    private String startSpace;

    private String endSpace;

    private String result;

    // Constructor with no player
    public Move(String player, String startSpace, String endSpace) {
        this.player = player;
        this.startSpace = startSpace;
        this.endSpace = endSpace;
    }

    public Move(){}



    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getStartSpace() {
        return startSpace;
    }

    public void setStartSpace(String startSpace) {
        this.startSpace = startSpace;
    }

    public String getEndSpace() {
        return endSpace;
    }

    public void setEndSpace(String endSpace) {
        this.endSpace = endSpace;
    }

    public int getId() {
        return id;
    }

}
