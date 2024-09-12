package com.userservice.repository;

import com.userservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole,Integer> {
    boolean existsUserRoleByRidAndUid(long rid,long uid);

}
