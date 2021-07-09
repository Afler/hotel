package com.company.hotel.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.EmbeddedParameters;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "HOTEL_CLIENT")
@Entity(name = "hotel_Client")
@NamePattern("%s %s|firstName,lastName")
public class Client extends StandardEntity {
    private static final long serialVersionUID = 8121670917680738189L;

    @NotNull
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "PATRONYMIC", nullable = false)
    private String patronymic;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "client")
    private List<RegistrationCard> registrationCards;

    @Embedded
    @EmbeddedParameters(nullAllowed = false)
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "CONTACTS_EMAIL")),
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "CONTACTS_PHONE_NUMBER"))
    })
    private Contacts contacts;

    public void setRegistrationCards(List<RegistrationCard> registrationCards) {
        this.registrationCards = registrationCards;
    }

    public List<RegistrationCard> getRegistrationCards() {
        return registrationCards;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}