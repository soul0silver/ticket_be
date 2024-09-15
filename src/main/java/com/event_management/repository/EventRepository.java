package com.event_management.repository;

import com.event_management.model.Event;
import com.event_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    @Query(value = "with b as (select t.eid,e.title,count(t.quantity) as times_buy from ticket t , event e " +
            "where t.eid=e.id group by t.eid order by times_buy desc) " +
            "select b.eid as id ,b.title,rank() over (order by b.times_buy desc ) ranks  from b  limit 10", nativeQuery = true)
    List<Map<String,Object>> getRank();
}
