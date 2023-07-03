package org.sanmarcux.samples.sakila.dao.model;

// Generated 20/10/2012 11:23:03 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FilmText generated by hbm2java
 */
@Entity
@Table(name = "film_text", catalog = "sakila")
public class FilmText implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private short filmId;
    private String title;
    private String description;

    public FilmText() {
    }

    public FilmText(short filmId, String title) {
        this.filmId = filmId;
        this.title = title;
    }

    public FilmText(short filmId, String title, String description) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
    }

    @Id
    @Column(name = "film_id", unique = true, nullable = false)
    public short getFilmId() {
        return this.filmId;
    }

    public void setFilmId(short filmId) {
        this.filmId = filmId;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description", length = 65535)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}