package org.sanmarcux.samples.sakila.business.impl;

import org.modelmapper.ModelMapper;
import org.sanmarcux.samples.sakila.business.ActorBusiness;
import org.sanmarcux.samples.sakila.dao.ActorFilmCountDTO;
import org.sanmarcux.samples.sakila.dao.ActorRepository;
import org.sanmarcux.samples.sakila.dao.FilmActorRepository;
import org.sanmarcux.samples.sakila.dao.model.Actor;
import org.sanmarcux.samples.sakila.dao.model.FilmActor;
import org.sanmarcux.samples.sakila.dao.model.FilmActorId;
import org.sanmarcux.samples.sakila.dto.ActorDTO;
import org.sanmarcux.samples.sakila.dto.FilmDTO;
import org.sanmarcux.samples.sakila.exceptions.ActorNotFoundException;
import org.sanmarcux.samples.sakila.exceptions.OperationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.sanmarcux.samples.sakila.exceptions.NotFoundException;

//import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorBusinessImpl implements ActorBusiness {

    private final ActorRepository actorRepository;
    private final FilmActorRepository filmActorRepository;

    private final ModelMapper modelMapper;

    @Autowired
    private ActorBusinessImpl(ActorRepository actorRepository,
                              FilmActorRepository filmActorRepository,
                              ModelMapper modelMapper) {
        this.actorRepository = actorRepository;
        this.filmActorRepository = filmActorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<ActorDTO> list(final Pageable pageable) {
        Page<Actor> actors = actorRepository.findAll(pageable);

        return new PageImpl<>(
                actors.stream()
                        .map(actor -> modelMapper.map(actor, ActorDTO.class))
                        .collect(Collectors.toList()),
                actors.getPageable(), actors.getTotalElements());
    }

    @Override
    public ActorDTO create(final ActorDTO payload) {
        if (payload.getActorId() != null) {
            throw new OperationNotAllowedException();
        }

        return modelMapper.map(
                actorRepository.save(
                        modelMapper.map(payload, Actor.class)), ActorDTO.class);
    }

    @Override
    public ActorDTO modify(final Short actorId, final ActorDTO payload) {
        Actor actor = modelMapper.map(payload, Actor.class);
        actor.setActorId(actorId);

        actorRepository.save(actor);

        return modelMapper.map(actor, ActorDTO.class);
    }

    @Override
    public ActorDTO get(final Short actorId) {
        return actorRepository.findById(actorId)
                .map(actor -> modelMapper.map(actor, ActorDTO.class))
                .orElseThrow(() -> new ActorNotFoundException(actorId));
    }

    @Override
    public void delete(final Short actorId) {
        actorRepository.deleteById(actorId);
    }

    @Override
    public void createFilmParticipation(final Short actorId, final Short filmId) {
        FilmActor filmActor = new FilmActor();
        filmActor.setId(new FilmActorId(actorId, filmId));
        filmActorRepository.save(filmActor);
    }

    @Override
    public FilmDTO getFilm(final Short actorId, final Short filmId) {
        return filmActorRepository.findById(new FilmActorId(actorId, filmId))
                .map(filmActor -> modelMapper.map(filmActor.getFilm(), FilmDTO.class))
                .orElseThrow(() -> new OperationNotAllowedException("The actor doesn't participate in film"));
    }

    @Override
    public void deleteFilm(final Short actorId, final Short filmId) {
        filmActorRepository.deleteById(new FilmActorId(actorId, filmId));
    }
    @Override
    public List<Actor> searchActorsByLastName(String lastName) {
        return actorRepository.findByLastName(lastName);
    }
    
    @Override
    public List<Actor> searchActorsByFirstName(String firstName) {
        return actorRepository.findByFirstName(firstName);
    }
   /* @Override
    public Actor updateActorLastName(Short actorid, String lastName) {
        Actor actor = actorRepository.findById(actorid)
                .orElseThrow(() -> new NotFoundException("Actor not found with id: " + actorid));

        actor.setLastName(lastName);
        return actorRepository.save(actor);
    }
    @Override
    public List<ActorFilmCountDTO> findTop10ActorsByFilmCount() {
        return actorRepository.findTop10ActorsByFilmCount();
    }
    */
}
