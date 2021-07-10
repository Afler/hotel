package com.company.hotel.web.screens.registrationcard;

import com.company.hotel.entity.Apartments;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.RegistrationCard;

import javax.inject.Inject;
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
    private Button removeBookFromApartments;
    @Inject
    private DataManager dataManager;

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
            changeDateIfPaymentButton.setEnabled(registrationCardsTable.getSingleSelected().getIsPayment());
            removeBookFromApartments.setEnabled(registrationCardsTable.getSingleSelected().getIsCOVID());
        }
    }

    @Subscribe("removeBookFromApartments")
    public void onRemoveBookFromApartmentsClick(Button.ClickEvent event) {
        RegistrationCard selectedRegCard = registrationCardsTable.getSingleSelected();
        if (selectedRegCard.getIsPayment()) {
            selectedRegCard.setIsPayment(false);
            selectedRegCard.setPaymentDate(null);
            dataManager.commit(selectedRegCard);
        }

        Apartments selectedApartments = dataManager.load(Apartments.class).query("select ap from hotel_Apartments ap where " +
                "ap.number = :number")
                .parameter("number", selectedRegCard.getApartments().getNumber())
                .one();
        List<RegistrationCard> registrationCardList = dataManager.load(RegistrationCard.class).query("select e from hotel_RegistrationCard e where " +
                "e.apartments = :apartments")
                .parameter("apartments", selectedApartments).list();
        if (registrationCardList.size() <= 1) {
            selectedApartments.setIsBooked(false);
            selectedApartments.setIsFree(true);
            dataManager.commit(selectedApartments);
        }
    }
}