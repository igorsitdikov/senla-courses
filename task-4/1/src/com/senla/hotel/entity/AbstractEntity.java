package com.senla.hotel.entity;

public abstract class AbstractEntity {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
