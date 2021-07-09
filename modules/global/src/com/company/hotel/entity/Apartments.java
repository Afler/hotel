package com.company.hotel.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Table(name = "HOTEL_APARTMENTS")
@Entity(name = "hotel_Apartments")
@NamePattern("%s|number")
public class Apartments extends StandardEntity {
    private static final long serialVersionUID = 5857474623979157808L;

    @NotNull
    @Column(name = "NUMBER_", nullable = false, unique = true)
    @Positive
    private Integer number;

    @Column(name = "IS_BOOKED")
    private Boolean isBooked = false;

    @Column(name = "IS_FREE")
    private Boolean isFree = true;

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Boolean isBooked) {
        this.isBooked = isBooked;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}