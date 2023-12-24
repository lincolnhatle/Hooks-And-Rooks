package com.backend.Player;
import com.backend.Move.Move;
import com.backend.Punch.Punch;
import jakarta.persistence.*;

import com.backend.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.backend.Users.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int HP;

    @OneToMany
    private List<Move> Movelist;

    @OneToMany
    private List<Punch> PunchList;

    @OneToOne
    private Users user;

    public Player(Users user) {
        this.HP = HP;
        this.Movelist = new ArrayList<Move>();
        this.PunchList = new ArrayList<Punch>();
        this.user = user;
    }

    public Player() {}

    public List<Move> getMovelist() { return Movelist; }

    public void setMovelist(List<Move> Movelist) {this.Movelist = Movelist; }

    public int getHP() {return HP; }

    public void setHP(int HP) {this.HP = HP; }

    public Users getUser(){
        return user;
    }

    public void setUser(Users user){
        this.user = user;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public List<Punch> getPunchList() {
        return PunchList;
    }

    public void setPunchList(List<Punch> punchList) {
        PunchList = punchList;
    }
}
