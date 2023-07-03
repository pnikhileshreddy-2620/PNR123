package org.sanmarcux.samples.sakila.business;

import java.util.Collection;
import java.util.List;

import org.sanmarcux.samples.sakila.dto.ActorDTO;
import org.sanmarcux.samples.sakila.dto.FilmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.sanmarcux.samples.sakila.dao.ActorFilmCountDTO;
import org.sanmarcux.samples.sakila.dao.model.Actor;
public interface ActorBusiness {

    Page<ActorDTO> list(Pageable pageable);

    ActorDTO create(ActorDTO payload);

    ActorDTO modify(Short actorId, ActorDTO payload);
    List<Actor> searchActorsByLastName(String lastName);
    List<Actor> searchActorsByFirstName(String firstName);
    
    
    ActorDTO get(Short actorId);
    
    
//   Actor updateActorLastName(Short actorId, String lastName);
   
   
//    List<ActorFilmCountDTO> findTop10ActorsByFilmCount();

    void delete(Short actorId);

    void createFilmParticipation(Short actorId, Short payload);

    FilmDTO getFilm(Short actorId, Short filmId);

    void deleteFilm(Short actorId, Short filmId);
}
