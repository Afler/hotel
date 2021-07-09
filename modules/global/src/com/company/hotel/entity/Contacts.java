package com.company.hotel.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.cuba.core.entity.EmbeddableEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@MetaClass(name = "hotel_Contacts")
@Embeddable
public class Contacts extends EmbeddableEntity {
    private static final long serialVersionUID = -4859852324862466581L;

    @Column(name = "EMAIL")
    @Email
    private String email;

    @Column(name = "PHONE_NUMBER")
    @Pattern(message = "{msg://hotel_Contacts.phoneNumber.validation.Pattern}", regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}