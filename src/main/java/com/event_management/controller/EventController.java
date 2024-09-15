package com.event_management.controller;

import com.event_management.exception.BusinessException;
import com.event_management.model.Event;
import com.event_management.repository.EventRepository;
import com.event_management.security.user_principle.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventRepository eventRepository;

    @GetMapping("/list")
    public List<Event> events() {
        return eventRepository.findAll();
    }

    @Transactional(readOnly=true)
    @GetMapping("/{id}")
    public Event get(@PathVariable("id") String id) {
        return eventRepository.findById(Long.parseLong(id)).orElseThrow(()->new BusinessException("not found"));
    }

    @PostMapping("/add")
    public Event create(@RequestBody Event event) {
        UserPrinciple userDetails = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setUid(userDetails.getId());
        return eventRepository.save(event);
    }

    @PutMapping("/{id}")
    public Event edit(@PathVariable("id") Long id,@RequestBody Event event) {
        var eventOld = eventRepository.findById(id).orElseThrow(RuntimeException::new);
        event.setId(id);
        return eventRepository.save(event);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        eventRepository.deleteById(id);
    }

    @GetMapping("/rank")
    Object rank(){
        return eventRepository.getRank();
    }
}
