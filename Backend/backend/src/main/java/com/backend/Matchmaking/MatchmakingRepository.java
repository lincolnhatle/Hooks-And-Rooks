package com.backend.Matchmaking;

import com.backend.Player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public interface MatchmakingRepository extends JpaRepository<Matchmaking, Long> {
    Matchmaking findById(int id);

    @Transactional
    void deleteById(int id);
}

