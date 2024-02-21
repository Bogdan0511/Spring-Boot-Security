package com.example.demo.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public void addPlayer(Player player) {
        Optional<Player> playerByName = playerRepository.findPlayerByFirstNameAndLastName(player.getFirstName(), player.getLastName());
        if(playerByName.isPresent()) {
            throw new IllegalStateException("Player already exists!");
        }
        playerRepository.save(player);
    }

    public void deletePlayer(Long playerId) {
        if(!playerRepository.existsById(playerId)) {
            throw new IllegalStateException("Player with id " + playerId + " doesn't exist!");
        }
        playerRepository.deleteById(playerId);
    }

    @Transactional
    public void updatePlayer(Long playerId, Integer rating) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalStateException(
                        "Player with id " + playerId + " doesn't exist!"));
        if(50 <= rating && rating <= 99 && !Objects.equals(player.getRating(), rating)) {
            player.setRating(rating);
        }
        else throw new IllegalStateException("Rating must be between 50 and 99!");
    }
}
