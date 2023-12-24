package com.backend.Punch;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Punch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int punchType;

    public Punch(int punchType) {

        this.punchType = punchType;

    }

}
