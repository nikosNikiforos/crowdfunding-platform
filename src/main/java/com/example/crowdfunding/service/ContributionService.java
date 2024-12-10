package com.example.crowdfunding.service;

import com.example.crowdfunding.entities.Contribution;
import com.example.crowdfunding.repository.ContributionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ContributionService {

    private ContributionRepository contributionRepository;

    public ContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Transactional
    public void saveContribution (Contribution contribution){
        contributionRepository.save(contribution);
    }
}
