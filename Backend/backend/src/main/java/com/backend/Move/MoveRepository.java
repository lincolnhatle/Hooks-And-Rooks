package com.backend.Move;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;



public interface MoveRepository extends JpaRepository<Move, Long> {
    Move findById(int id);

    @Transactional
    void deleteById(int id);
}


