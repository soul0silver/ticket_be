package com.event_management.repository;

import com.event_management.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "select month(create_at) as `month`, sum(quantity*(select price from event where id=eid)) as receive " +
            "from ticket where eid =:eid group by month(create_at)", nativeQuery = true)
    List<Map<String, Object>> getChart(@Param("eid") long eid);

    @Query("select t from Ticket t where YEAR(t.createAt) = :year order by t.createAt desc ")
    Page<Ticket> findAllByYear(@Param("year")int year, Pageable pageable);
}
