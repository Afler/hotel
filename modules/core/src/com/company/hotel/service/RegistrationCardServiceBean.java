package com.company.hotel.service;

import com.company.hotel.entity.Apartments;
import com.company.hotel.entity.RegistrationCard;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service(RegistrationCardService.NAME)
public class RegistrationCardServiceBean implements RegistrationCardService {

    @Inject
    private DataManager dataManager;
    @Inject
    private Persistence persistence;

    @Override
    public void removeBookFromApartmentsByRegCard(RegistrationCard selectedRegCard) {
        // Сброс признаков оплаты
        if (selectedRegCard.getIsPayment()) {
            selectedRegCard.setIsPayment(false);
            selectedRegCard.setPaymentDate(null);
            dataManager.commit(selectedRegCard);
        }

        // Загрузить апартаменты переданной рег.карты
        Apartments selectedApartments = dataManager.load(Apartments.class).query("select ap from hotel_Apartments ap where " +
                "ap.number = :number")
                .parameter("number", selectedRegCard.getApartments().getNumber())
                .one();

        // Найти количество карточек с теми же апартаментами, что у переданной
        View view = new View(Apartments.class)
                .addProperty("number");
        LoadContext<RegistrationCard> registrationCardLoadContext = LoadContext.create(RegistrationCard.class)
                .setQuery(LoadContext.createQuery("select e from hotel_RegistrationCard e where " +
                        "e.apartments = :apartments")
                        .setParameter("apartments", selectedApartments)).setView(view);
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
                    .setId(selectedRegCard.getId()).setView(view);
            Date creationDate = dataManager.load(registrationCardLoadContext).getCreateTs();
            Date nowDate = new Date();

            long lifeTimeInHours = (nowDate.getTime() - creationDate.getTime()) / 1000 / 60 / 60;
            if (lifeTimeInHours / 24 > 1) {
                removeBookFromApartmentsByRegCard(selectedRegCard);
                return "Успешно";
            } else {
                return "Карточка регистрации создана всего " + lifeTimeInHours + "ч. назад";
            }
        }
        return "Ошибка: предоплата произведена";
    }
}