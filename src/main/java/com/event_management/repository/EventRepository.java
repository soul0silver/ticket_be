package com.event_management.repository;

import com.event_management.model.Event;
import com.event_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
}
