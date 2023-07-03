package org.sanmarcux.samples.sakila.dao;

public class ActorFilmCountDTO {
    private Short actorId;
    private String actorName;
    private int filmCount;

    public ActorFilmCountDTO(Short actorId, String actorName, int filmCount) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.filmCount = filmCount;
    }

    public Short getActorId() {
        return actorId;
    }

    public void setActorId(Short actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public int getFilmCount() {
        return filmCount;
    }

    public void setFilmCount(int filmCount) {
        this.filmCount = filmCount;
    }
}

