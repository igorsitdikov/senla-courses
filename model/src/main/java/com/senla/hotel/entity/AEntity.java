package com.senla.hotel.entity;

import java.io.Serializable;

public abstract class AEntity implements Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);
}
