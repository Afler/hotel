package com.company.hotel.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Table(name = "HOTEL_REGISTRATION_CARD")
@Entity(name = "hotel_RegistrationCard")
@NamePattern("%s|apartments")
public class RegistrationCard extends StandardEntity {
    private static final long serialVersionUID = -8736133548632889119L;

    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "CLIENT_ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Client client;

    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "APARTMENTS_ID")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Apartments apartments;

    @NotNull
    @Column(name = "ARRIVAL_DATE", nullable = false)
    private LocalDate arrivalDate;

    @NotNull
    @Column(name = "DEPARTURE_DATE", nullable = false)
    private LocalDate departureDate;

    @Column(name = "IS_PAYMENT", nullable = false)
    @NotNull
    private Boolean isPayment = false;

    @Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;

    @Column(name = "IS_PREPAYMENT", nullable = false)
    @NotNull
    private Boolean isPrepayment = false;

    @Column(name = "PREPAYMENT_DATE")
    private LocalDate prepaymentDate;

    @Column(name = "IS_COVID", nullable = false)
    @NotNull
    private Boolean isCOVID = false;

    public Boolean getIsCOVID() {
        return isCOVID;
    }

    public void setIsCOVID(Boolean isCOVID) {
        this.isCOVID = isCOVID;
    }

    public LocalDate getPrepaymentDate() {
        return prepaymentDate;
    }

    public void setPrepaymentDate(LocalDate prepaymentDate) {
        this.prepaymentDate = prepaymentDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean getIsPrepayment() {
        return isPrepayment;
    }

    public void setIsPrepayment(Boolean isPrepayment) {
        this.isPrepayment = isPrepayment;
    }

    public Boolean getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(Boolean isPayment) {
        this.isPayment = isPayment;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Apartments getApartments() {
        return apartments;
    }

    public void setApartments(Apartments apartments) {
        this.apartments = apartments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}