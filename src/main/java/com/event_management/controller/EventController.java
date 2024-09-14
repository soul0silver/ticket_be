package com.event_management.controller;

import com.event_management.model.Event;
import com.event_management.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventRepository eventRepository;

    @GetMapping("/")
    public List<Event> events() {
        return eventRepository.findAll();
    }

    @PostMapping("/")
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @PutMapping("/{id}")
    public Event edit(@PathVariable("id") Long id, Event event) {
        var eventOld = eventRepository.findById(id).orElseThrow(RuntimeException::new);
        BeanUtils.copyProperties(eventOld, event);
        return eventRepository.save(event);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        eventRepository.deleteById(id);
    }
}
