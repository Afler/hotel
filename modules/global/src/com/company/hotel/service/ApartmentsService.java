package com.company.hotel.service;

import com.company.hotel.entity.Apartments;

import java.util.List;

public interface ApartmentsService {
    String NAME = "hotel_ApartmentsService";

    List<Apartments> getFreeApartments();
}