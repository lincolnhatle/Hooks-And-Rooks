package com.backend.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findById(int id);

    @Transactional
    void deleteById(int id);

    Users findByUsername(String username);

}
