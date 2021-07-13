package com.company.hotel.service;

import com.company.hotel.entity.RegistrationCard;

public interface RegistrationCardService {
    String NAME = "hotel_RegistrationCardService";

    void removeBookFromApartmentsByRegCard(RegistrationCard selectedRegCard);

    String removeBookFromApartmentsCausePrepayment(RegistrationCard selectedRegCard);

}