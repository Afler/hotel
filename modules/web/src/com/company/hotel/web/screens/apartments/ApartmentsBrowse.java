package com.company.hotel.web.screens.apartments;

import com.company.hotel.service.ApartmentsService;
import com.company.hotel.web.screens.client.ClientBrowse;
import com.company.hotel.web.screens.registrationcard.RegistrationCardBrowse;
import com.company.hotel.web.screens.registrationcard.RegistrationCardEdit;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.Apartments;

import javax.inject.Inject;
import java.util.List;

@UiController("hotel_Apartments.browse")
@UiDescriptor("apartments-browse.xml")
@LookupComponent("apartmentsTable")
@LoadDataBeforeShow
public class ApartmentsBrowse extends StandardLookup<Apartments> {

    @Inject
    private ApartmentsService apartmentsService;

    @Inject
    private Screens screens;

    @Inject
    private GroupTable<Apartments> apartmentsTable;
    @Inject
    private Button bookApartmentsBtn;

    @Subscribe("apartmentsTable")
    public void onApartmentsTableSelection(Table.SelectionEvent<Apartments> event) {
        bookApartmentsBtn.setEnabled(true);
        Apartments apartments = apartmentsTable.getSingleSelected();
    }

    @Subscribe("bookApartmentsBtn")
    public void onBookApartmentsBtnClick1(Button.ClickEvent event) {
        List<Apartments> freeApartments = apartmentsService.getFreeApartments();
    }
}