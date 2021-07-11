package com.company.hotel.web.screens.registrationcard;

import com.company.hotel.entity.Apartments;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.RegistrationCard;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@UiController("hotel_RegistrationCard.browse")
@UiDescriptor("registration-card-browse.xml")
@LookupComponent("registrationCardsTable")
@LoadDataBeforeShow
public class RegistrationCardBrowse extends StandardLookup<RegistrationCard> {
    @Inject
    private Button changeDateIfPaymentButton;

    @Inject
    private GroupTable<RegistrationCard> registrationCardsTable;
    @Inject
    private Screens screens;
    @Inject
    private Button removeBookFromApartmentsCausePCR;
    @Inject
    private DataManager dataManager;
    @Inject
    private Button removeBookFromApartmentsCausePrepayment;
    @Inject
    private Notifications notifications;

    @Subscribe("changeDateIfPaymentButton")
    public void onChangeDateIfPaymentButtonClick(Button.ClickEvent event) {
        RegistrationCard registrationCard = registrationCardsTable.getSingleSelected();
        if (registrationCard != null) {
            RegistrationCardEdit registrationCardEditScreen = screens.create(RegistrationCardEdit.class);
            registrationCardEditScreen.setEntityToEdit(registrationCard);
            registrationCardEditScreen.lockAllNonArrivalDateFields();
            registrationCardEditScreen.show();
        }
    }

    @Subscribe("registrationCardsTable")
    public void onRegistrationCardsTableSelection(Table.SelectionEvent<RegistrationCard> event) {
        RegistrationCard registrationCard = registrationCardsTable.getSingleSelected();
        if (registrationCard != null) {
            changeDateIfPaymentButton.setEnabled(registrationCard.getIsPayment());
            removeBookFromApartmentsCausePCR.setEnabled(registrationCard.getIsCOVID());
            removeBookFromApartmentsCausePrepayment.setEnabled(!registrationCard.getIsPrepayment());
        }
    }

    @Subscribe("removeBookFromApartmentsCausePCR")
    public void onRemoveBookFromApartmentsCausePCRClick(Button.ClickEvent event) {
        RegistrationCard selectedRegCard = registrationCardsTable.getSingleSelected();
        if (selectedRegCard.getIsPayment()) {
            selectedRegCard.setIsPayment(false);
            selectedRegCard.setPaymentDate(null);
            dataManager.commit(selectedRegCard);
        }

        removeBookFromApartmentsByRegCard(selectedRegCard);
        notifications.create().withCaption("Успешно").show();
    }

    @Subscribe("removeBookFromApartmentsCausePrepayment")
    public void onRemoveBookFromApartmentsCausePrepaymentClick(Button.ClickEvent event) {
        RegistrationCard selectedRegCard = registrationCardsTable.getSingleSelected();
        if (!selectedRegCard.getIsPrepayment()) {
            View view = new View(RegistrationCard.class)
                    .addProperty("createTs");
            LoadContext<RegistrationCard> registrationCardLoadContext = LoadContext.create(RegistrationCard.class)
                    .setId(selectedRegCard.getId()).setView(view);
            Date creationDate = dataManager.load(registrationCardLoadContext).getCreateTs();
            Date nowDate = new Date();
            long lifeTimeInHours = (nowDate.getTime() - creationDate.getTime()) / 1000 / 60 / 60;
            notifications.create().withCaption("С момента создания объекта прошло: " +
                    lifeTimeInHours +
                    "ч.").show();
            if (lifeTimeInHours / 24 > 1) {
                removeBookFromApartmentsByRegCard(selectedRegCard);
            }
        }
    }

    public void removeBookFromApartmentsByRegCard(RegistrationCard selectedRegCard) {
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

    ;
}