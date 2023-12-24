package com.backend.Match;

import com.backend.Matchmaking.Matchmaking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public interface MatchRepository extends JpaRepository<Match, Long> {
    Match findById(int id);

    @Transactional
    void deleteById(int id);
}