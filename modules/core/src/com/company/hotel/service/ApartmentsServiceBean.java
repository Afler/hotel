package com.company.hotel.service;

import com.company.hotel.entity.Apartments;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(ApartmentsService.NAME)
public class ApartmentsServiceBean implements ApartmentsService {
    @Inject
    private DataManager dataManager;

    @Override
    public List<Apartments> getFreeApartments() {
        return dataManager.load(Apartments.class)
                .query("select e from hotel_Apartments e where " +
                        "e.isFree = true")
                .viewProperties("number", "isBooked", "isFree")
                .list();
    }
}