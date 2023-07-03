package org.sanmarcux.samples.sakila.dao;

//import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.sanmarcux.samples.sakila.dao.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ActorRepository extends JpaRepository<Actor, Short> {

    /**
     * This method will find an DTOActor instance in the database by its last name.
     * Note that this method is not implemented and its working code will be
     * automatically generated from its signature by Spring Data JPA.
     */
    List<Actor> findByLastName(String lastName);

	List<Actor> findByFirstName(String firstName);
/*
	Optional<Actor> findById(Long actorId);
	@Query("SELECT new com.example.dto.ActorFilmCountDTO(a.id, CONCAT(a.firstName, ' ', a.lastName), COUNT(f)) " +
            "FROM Actor a " +
            "JOIN a.films f " +
            "GROUP BY a.id, a.firstName, a.lastName " +
            "ORDER BY COUNT(f) DESC")
  

	List<ActorFilmCountDTO> findTop10ActorsByFilmCount();
	*/
}
