package com.company.hotel.service;

import com.company.hotel.entity.Apartments;
import com.company.hotel.entity.RegistrationCard;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.View;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service(RegistrationCardService.NAME)
public class RegistrationCardServiceBean implements RegistrationCardService {

    @Inject
    private DataManager dataManager;
    @Inject
    private TimeSource timeSource;

    @Override
    public void removeBookFromApartmentsByRegCard(RegistrationCard selectedRegCard) {
        if (selectedRegCard.getIsPayment()) {
            selectedRegCard.setIsPayment(false);
            selectedRegCard.setPaymentDate(null);
            dataManager.commit(selectedRegCard);
        }

        Apartments selectedApartments = getRegCardApartments(selectedRegCard);
        View view = new View(Apartments.class)
                .addProperty("number");
        LoadContext<RegistrationCard> registrationCardLoadContext = LoadContext.create(RegistrationCard.class)
                .setQuery(LoadContext.createQuery("select e from hotel_RegistrationCard e where " +
                        "e.apartments = :apartments")
                        .setParameter("apartments", selectedApartments))
                .setView(view);
        long count = dataManager.getCount(registrationCardLoadContext);
        if (count <= 1) {
            selectedApartments.setIsBooked(false);
            selectedApartments.setIsFree(true);
            dataManager.commit(selectedApartments);
        }
    }

    @Override
    public String removeBookFromApartmentsCausePrepayment(RegistrationCard selectedRegCard) {
        if (!selectedRegCard.getIsPrepayment()) {
            View view = new View(RegistrationCard.class)
                    .addProperty("createTs");
            LoadContext<RegistrationCard> registrationCardLoadContext = LoadContext.create(RegistrationCard.class)
                    .setId(selectedRegCard.getId())
                    .setView(view);

            Date nowDate = timeSource.currentTimestamp();
            Date creationDate = dataManager.load(registrationCardLoadContext).getCreateTs();

            if (creationDate.before(DateUtils.addDays(nowDate, -1))) {
                removeBookFromApartmentsByRegCard(selectedRegCard);
                return "Успешно";
            } else {
                return "Карточка регистрации создана менее 24 ч. назад";
            }
        }
        return "Ошибка: предоплата произведена";
    }

    private Apartments getRegCardApartments(RegistrationCard registrationCard) {
        return dataManager.load(Apartments.class).query("select ap from hotel_Apartments ap where " +
                "ap.number = :number")
                .parameter("number", registrationCard.getApartments().getNumber())
                .viewProperties("id","isBooked","isFree")
                .one();
    }
}