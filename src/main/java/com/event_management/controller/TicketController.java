package com.event_management.controller;

import com.event_management.model.Ticket;
import com.event_management.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {
    private final TicketRepository ticketRepository;

    @PostMapping
    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
