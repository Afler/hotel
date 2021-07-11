package com.company.hotel.web.screens.registrationcard;

import com.company.hotel.entity.Apartments;
import com.company.hotel.entity.Client;
import com.company.hotel.web.screens.apartments.ApartmentsBrowse;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FluentLoader;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.RegistrationCard;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.UUID;

@UiController("hotel_RegistrationCard.edit")
@UiDescriptor("registration-card-edit.xml")
@EditedEntityContainer("registrationCardDc")
@LoadDataBeforeShow
public class RegistrationCardEdit extends StandardEditor<RegistrationCard> {
    @Inject
    private DateField<LocalDate> paymentDateField;

    @Inject
    private DateField<LocalDate> prepaymentDateField;

    @Inject
    private CheckBox isPrepaymentField;

    @Inject
    private CheckBox isPaymentField;

    @Inject
    private PickerField<Client> clientField;

    @Inject
    private CheckBox isCOVIDField;

    @Inject
    private PickerField<Apartments> apartmentsField;

    @Inject
    private DateField<LocalDate> departureDateField;
    @Inject
    private DataManager dataManager;
    @Inject
    private Screens screens;

    @Subscribe("isPaymentField")
    public void onIsPaymentFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        paymentDateField.setVisible(Boolean.TRUE.equals(event.getComponent().getValue()));
        paymentDateField.setValue(null);
    }

    @Subscribe("isPrepaymentField")
    public void onIsPrepaymentFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        prepaymentDateField.setVisible(Boolean.TRUE.equals(event.getComponent().getValue()));
        prepaymentDateField.setValue(null);
    }

    public void lockApartmentsField() {
        apartmentsField.setEnabled(false);
    }

    public void lockAllNonArrivalDateFields() {
        isCOVIDField.setEnabled(false);
        isPaymentField.setEnabled(false);
        isPrepaymentField.setEnabled(false);
        clientField.setEnabled(false);
        apartmentsField.setEnabled(false);
        paymentDateField.setEnabled(false);
        prepaymentDateField.setEnabled(false);
        departureDateField.setEnabled(false);
    }

    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        Apartments apartmentsToUpdate = dataManager.load(Apartments.class).id(this.getEditedEntity().getApartments().getId()).one();
        apartmentsToUpdate.setIsBooked(true);
        apartmentsToUpdate.setIsFree(false);
        dataManager.commit(apartmentsToUpdate);
    }

    @Subscribe("commitAndCloseBtn")
    public void onCommitAndCloseBtnClick(Button.ClickEvent event) {
        ApartmentsBrowse apartmentsBrowseScreen = screens.create(ApartmentsBrowse.class);
        apartmentsBrowseScreen.show();
    }
}