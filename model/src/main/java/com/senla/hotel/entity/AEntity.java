package com.senla.hotel.entity;

import java.io.Serializable;

public abstract class AEntity implements Serializable {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
