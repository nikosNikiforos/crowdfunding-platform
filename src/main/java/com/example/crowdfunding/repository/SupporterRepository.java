package com.example.crowdfunding.repository;

import com.example.crowdfunding.entities.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupporterRepository extends JpaRepository<Supporter, Integer> {
}
