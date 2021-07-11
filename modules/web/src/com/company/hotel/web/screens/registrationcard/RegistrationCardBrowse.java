package com.company.hotel.web.screens.registrationcard;

import com.company.hotel.entity.Apartments;
import com.company.hotel.service.RegistrationCardService;
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
    private Button changeDateIfPaymentBtn;

    @Inject
    private GroupTable<RegistrationCard> registrationCardsTable;

    @Inject
    private Screens screens;

    @Inject
    private Button removeBookFromApartmentsCausePCRBtn;

    @Inject
    private DataManager dataManager;

    @Inject
    private Button removeBookFromApartmentsCausePrepaymentBtn;

    @Inject
    private Notifications notifications;

    @Inject
    private RegistrationCardService registrationCardService;

    @Subscribe("changeDateIfPaymentBtn")
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
            changeDateIfPaymentBtn.setEnabled(registrationCard.getIsPayment());
            removeBookFromApartmentsCausePCRBtn.setEnabled(registrationCard.getIsCOVID());
            removeBookFromApartmentsCausePrepaymentBtn.setEnabled(!registrationCard.getIsPrepayment());
        }
    }

    @Subscribe("removeBookFromApartmentsCausePCRBtn")
    public void onRemoveBookFromApartmentsCausePCRClick(Button.ClickEvent event) {
        RegistrationCard selectedRegCard = registrationCardsTable.getSingleSelected();
        registrationCardService.removeBookFromApartmentsByRegCard(selectedRegCard);
        notifications.create().withCaption("Успешно").show();
    }

    @Subscribe("removeBookFromApartmentsCausePrepaymentBtn")
    public void onRemoveBookFromApartmentsCausePrepaymentClick(Button.ClickEvent event) {
        RegistrationCard selectedRegCard = registrationCardsTable.getSingleSelected();
        notifications.create().withCaption(registrationCardService.removeBookFromApartmentsCausePrepayment(selectedRegCard)).show();
    }
}