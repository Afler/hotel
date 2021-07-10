package com.company.hotel.web.screens.registrationcard;

import com.company.hotel.entity.Apartments;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.RegistrationCard;

import javax.inject.Inject;
import java.time.LocalDate;

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
    private PickerField<Apartments> apartmentsField;

    @Subscribe("isPaymentField")
    public void onIsPaymentFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        paymentDateField.setVisible(Boolean.TRUE.equals(event.getComponent().getValue()));
    }

    @Subscribe("isPrepaymentField")
    public void onIsPrepaymentFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        prepaymentDateField.setVisible(Boolean.TRUE.equals(event.getComponent().getValue()));
    }

    public void setApartmentsField(Apartments apartmentsField) {
        this.apartmentsField.setValue(apartmentsField);
    }

    public void lockApartmentsField() {
        apartmentsField.setEnabled(false);
    }
}