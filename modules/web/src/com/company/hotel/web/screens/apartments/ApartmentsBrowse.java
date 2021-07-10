package com.company.hotel.web.screens.apartments;

import com.company.hotel.entity.Apartments;
import com.company.hotel.entity.RegistrationCard;
import com.company.hotel.web.screens.registrationcard.RegistrationCardEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("hotel_Apartments.browse")
@UiDescriptor("apartments-browse.xml")
@LookupComponent("apartmentsTable")
@LoadDataBeforeShow
public class ApartmentsBrowse extends StandardLookup<Apartments> {

    @Inject
    private Screens screens;

    @Inject
    private GroupTable<Apartments> apartmentsTable;
    @Inject
    private Button bookApartmentsBtn;
    @Inject
    private DataManager dataManager;

    @Subscribe("apartmentsTable")
    public void onApartmentsTableSelection(Table.SelectionEvent<Apartments> event) {
        bookApartmentsBtn.setEnabled(apartmentsTable.getSingleSelected().getIsFree());
    }

    @Subscribe("bookApartmentsBtn")
    public void onBookApartmentsBtnClick1(Button.ClickEvent event) {
        RegistrationCard registrationCard = dataManager.create(RegistrationCard.class);
        Apartments apartments = apartmentsTable.getSingleSelected();
        registrationCard.setApartments(apartments);
        RegistrationCardEdit registrationCardEditScreen = screens.create(RegistrationCardEdit.class);
        registrationCardEditScreen.setEntityToEdit(registrationCard);
        registrationCardEditScreen.lockApartmentsField();
        registrationCardEditScreen.show();
    }
}