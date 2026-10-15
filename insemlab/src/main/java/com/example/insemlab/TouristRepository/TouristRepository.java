package com.example.insemlab.TouristRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.insemlab.tourist.Tourist;

public interface TouristRepository
        extends JpaRepository<Tourist, Long> {
}