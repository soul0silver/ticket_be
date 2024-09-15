package com.event_management.controller;

import com.event_management.model.Ticket;
import com.event_management.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TicketController {
    private final TicketRepository ticketRepository;

    @PostMapping("/tickets")
    public Ticket create(@RequestBody Ticket ticket) {
        ticket.setCreateAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    @GetMapping("/chart/{id}")
    public Object getChart(@PathVariable String id) {
        return ticketRepository.getChart(Long.parseLong(id));
    }

    @GetMapping("/tickets")
    public Page<Ticket> list(@RequestParam int year, @RequestParam int page) {
        Pageable pageable= PageRequest.of(page,20);
        return ticketRepository.findAllByYear(year, pageable);
    }
}
