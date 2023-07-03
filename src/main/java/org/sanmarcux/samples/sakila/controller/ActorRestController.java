package org.sanmarcux.samples.sakila.controller;

import org.sanmarcux.samples.sakila.business.ActorBusiness;
import org.springframework.http.HttpStatus;

import org.sanmarcux.samples.sakila.business.FilmBusiness;
import org.sanmarcux.samples.sakila.dao.ActorFilmCountDTO;
import org.sanmarcux.samples.sakila.dao.model.Actor;
import org.sanmarcux.samples.sakila.dao.model.ActorRequest;
import org.sanmarcux.samples.sakila.dto.ActorDTO;
import org.sanmarcux.samples.sakila.dto.FilmDTO;
import org.sanmarcux.samples.sakila.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * Created on 21/04/2018.
 *
 * @author Cesardl
 */
@RestController
@RequestMapping("api/actors")
public class ActorRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ActorRestController.class);

    private final ActorBusiness actorBusiness;
    private final FilmBusiness filmBusiness;

    @Autowired
    public ActorRestController(ActorBusiness actorBusiness, FilmBusiness filmBusiness) {
        this.actorBusiness = actorBusiness;
        this.filmBusiness = filmBusiness;
    }

    /**
     * List of actors obtained from database.
     *
     * @return actors
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<ActorDTO>> listActors(final Pageable pageable) {
        LOG.info("Invoking Rest Service listActors");
        Page<ActorDTO> actors = actorBusiness.list(pageable);

        if (actors.hasContent()) {
            return ResponseEntity.ok().body(actors);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Create a new actor and save it in the database.
     *
     * @param payload request body
     * @return a created actor
     */
    @PostMapping
    public ResponseEntity<?> createActor(@Valid @RequestBody final ActorDTO payload) {
        LOG.info("Invoking Rest Service createActor");

        ActorDTO result = actorBusiness.create(payload);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{actorId}")
                .buildAndExpand(result.getActorId()).toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Read an actor by id from the database.
     *
     * @param actorId actor resource id
     * @return an actor
     */
    @GetMapping("/{actorId}")
    @ResponseBody
    public ActorDTO getActor(@PathVariable Short actorId) {
        LOG.info("Invoking Rest Service getActor");
        return actorBusiness.get(actorId);
    }

    /**
     * Update an actor record in the database.
     *
     * @param actorId actor resource id
     * @param payload request body
     * @return a modified actor
     */
    @PatchMapping("/{actorId}")
    public ActorDTO modifyActor(@PathVariable final Short actorId, @RequestBody final ActorDTO payload) {
        LOG.info("Invoking Rest Service modifyActor");
        return actorBusiness.modify(actorId, payload);
    }

    /**
     * Delete an actor from the database.
     *
     * @param actorId actor resource id
     */
    @DeleteMapping("/{actorId}")
    public ResponseEntity<?> deleteActor(@PathVariable final Short actorId) {
        LOG.info("Invoking Rest Service deleteActor");
        actorBusiness.delete(actorId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{actorId}/films")
    public ResponseEntity<List<FilmDTO>> listActorFilms(@PathVariable final Short actorId) {
        LOG.info("Invoking Rest Service listActorFilms");
        List<FilmDTO> films = filmBusiness.findFilmsByActor(actorId);
        if (films.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(films);
    }

    @GetMapping("/{actorId}/films/{filmId}")
    public FilmDTO getActorFilm(@PathVariable final Short actorId, @PathVariable final Short filmId) {
        LOG.info("Invoking Rest Service getActorFilm");

        return actorBusiness.getFilm(actorId, filmId);
    }

    @PutMapping("/{actorId}/films/{filmId}")
    public ResponseEntity<?> modifyActorFilm(@PathVariable final Short actorId, @PathVariable final Short filmId, @RequestBody final FilmDTO payload) {
        LOG.info("Invoking Rest Service modifyActorFilm");

        actorBusiness.createFilmParticipation(actorId, filmId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{actorId}/films/{filmId}")
    public ResponseEntity<?> deleteActorFilm(@PathVariable final Short actorId, @PathVariable final Short filmId) {
        LOG.info("Invoking Rest Service deleteActorFilm");
        actorBusiness.deleteFilm(actorId, filmId);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/lastname/{ln}")
    public Collection<Actor> searchActorsByLastName(@PathVariable("ln") String lastName) {
        return actorBusiness.searchActorsByLastName(lastName);
    }
    @GetMapping("/firstname/{fn}")
    public List<Actor> searchActorsByFirstName(@PathVariable("fn") String firstName) {
        return actorBusiness.searchActorsByFirstName(firstName);
    }
    /*@PutMapping("/update/lastname/{id}")
    public Actor updateActorLastName(@PathVariable("id") Long id, @RequestBody ActorRequest actorRequest) {
        return actorBusiness.updateActorLastName(id, actorRequest.getLastName());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }
    @GetMapping("/toptenbyfilmcount")
    public List<ActorFilmCountDTO> findTop10ActorsByFilmCount() {
        return actorBusiness.findTop10ActorsByFilmCount();
    }*/
    
}
