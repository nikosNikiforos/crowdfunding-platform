package com.example.crowdfunding.repository;

import com.example.crowdfunding.entities.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Integer> {
    Creator findByUsername(String username);
    Creator findByEmail(String email);

}
