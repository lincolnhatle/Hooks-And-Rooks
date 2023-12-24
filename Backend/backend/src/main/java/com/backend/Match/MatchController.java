package com.backend.Match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatchController {

    @Autowired
    MatchRepository matchrepository;


    @GetMapping(path = "/match")
    public List<Match> getMatches() {
        return matchrepository.findAll();
    }

    @GetMapping(path = "/match/{id}")
    public Match getMatch(@PathVariable int id) {
        Match match = matchrepository.findById(id);
        return match;
    }

}
