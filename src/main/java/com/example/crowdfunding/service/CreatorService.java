package com.example.crowdfunding.service;

import com.example.crowdfunding.entities.Creator;
import com.example.crowdfunding.repository.CreatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;

    public CreatorService(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    public List<Creator> getAllCreators() {
        return creatorRepository.findAll();
    }

    public void saveCreator(Creator creator) {
        if (creatorRepository.findByUsername(creator.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (creatorRepository.findByEmail(creator.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        creatorRepository.save(creator);
    }

    public Creator getCreatorById(Integer id) {
        return creatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Creator not found"));
    }

    // Διόρθωση εδώ: Μετονομασία σε findByUsername
    public Creator findByUsername(String username) {
        return creatorRepository.findByUsername(username);
    }
}
