package com.example.insemlab.TouristController;

import com.example.insemlab.tourist.Tourist;
import com.example.insemlab.TouristRepository.TouristRepository;
import com.example.insemlab.TokenConfig.TokenConfig;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    private final TouristRepository repository;
    private final TokenConfig tokenConfig;

    public TouristController(
            TouristRepository repository,
            TokenConfig tokenConfig) {
        this.repository = repository;
        this.tokenConfig = tokenConfig;
    }

    // Create a tourist
    @PostMapping
    public Tourist createTourist(@RequestBody Tourist tourist) {
        return repository.save(tourist);
    }

    // Update a tourist
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTourist(
            @PathVariable Long id,
            @RequestHeader(value = "token", required = false)
            String token,
            @RequestBody Tourist updatedTourist) {

        try {
            if (!tokenConfig.isValid(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid token");
            }

            Tourist tourist = repository.findById(id)
                    .orElse(null);

            if (tourist == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Tourist not found");
            }

            tourist.setName(updatedTourist.getName());
            tourist.setLocation(updatedTourist.getLocation());
            tourist.setBudget(updatedTourist.getBudget());

            repository.save(tourist);

            return ResponseEntity.ok(
                    "Tourist updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error");
        }
    }
}