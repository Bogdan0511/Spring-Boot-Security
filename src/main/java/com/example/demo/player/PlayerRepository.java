package com.example.demo.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE p.firstName=?1 AND p.lastName=?2")
    Optional<Player> findPlayerByFirstNameAndLastName(String firstName, String lastName);
}
