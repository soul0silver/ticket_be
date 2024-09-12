package com.event_management.repository;

import com.event_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Set<Role> findRoleByName(String name);
    List<Role> findRoleByUsersId(Long id);
}
