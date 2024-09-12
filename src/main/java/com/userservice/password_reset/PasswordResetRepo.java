package com.userservice.password_reset;

import com.userservice.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepo extends JpaRepository<PasswordResetToken,Integer> {
    PasswordResetToken findByToken(String token);
}
