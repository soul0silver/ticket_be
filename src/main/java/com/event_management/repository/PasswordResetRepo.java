package com.event_management.repository;

import com.event_management.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepo extends JpaRepository<PasswordResetToken,Integer> {
    PasswordResetToken findByToken(String token);
}
