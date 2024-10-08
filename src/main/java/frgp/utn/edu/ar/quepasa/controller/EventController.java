package frgp.utn.edu.ar.quepasa.controller;

import frgp.utn.edu.ar.quepasa.data.ResponseError;
import frgp.utn.edu.ar.quepasa.data.request.event.EventPatchEditRequest;
import frgp.utn.edu.ar.quepasa.model.Event;
import frgp.utn.edu.ar.quepasa.model.User;
import frgp.utn.edu.ar.quepasa.service.AuthenticationService;
import frgp.utn.edu.ar.quepasa.service.EventService;
import frgp.utn.edu.ar.quepasa.service.validators.ValidatorBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final AuthenticationService authenticationService;

    EventController(EventService eventService, AuthenticationService authenticationService) {
        this.eventService = eventService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<Page<Event>> getEvents(@RequestParam(defaultValue = "") String q, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.getEvents(q, pageable));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventPatchEditRequest event) {
        User me = authenticationService.getCurrentUserOrDie();
        return ResponseEntity.ok(eventService.create(event, me));
    }

    @PostMapping("/{eventId}/rsvp")
    public ResponseEntity<?> confirmEventAssistance(@PathVariable UUID eventId) {
        User me = authenticationService.getCurrentUserOrDie();
        return ResponseEntity.ok(eventService.confirmEventAssistance(eventId, me));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getEventsByUser(@PathVariable String username, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.findByUsername(username, pageable));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getEventsByAuthUser(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) {
        User me = authenticationService.getCurrentUserOrDie();
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.findByOp(me, pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable UUID id, @RequestBody EventPatchEditRequest event) {
        return ResponseEntity.ok(eventService.update(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable UUID id) {
        eventService.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    /// Excepciones
    ///
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ResponseError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidatorBuilder.ValidationError.class)
    public ResponseEntity<ValidatorBuilder.ValidationError> handleValidationError(ValidatorBuilder.ValidationError e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }

    @PostMapping("/{eventId}/neighbourhoods")
    public ResponseEntity<Event> addNeighbourhoodsToEvent(@PathVariable UUID eventId, @RequestBody Set<Long> neighbourhoodIds) {
        Event updatedEvent = eventService.addNeighbourhoodsToEvent(eventId, neighbourhoodIds);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{eventId}/neighbourhoods")
    public ResponseEntity<Event> removeNeighbourhoodsFromEvent(@PathVariable UUID eventId, @RequestBody Set<Long> neighbourhoodIds) {
        Event updatedEvent = eventService.removeNeighbourhoodsFromEvent(eventId, neighbourhoodIds);
        return ResponseEntity.ok(updatedEvent);
    }

}
