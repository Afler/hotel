package com.company.hotel.web.screens.apartments;

import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.Apartments;

@UiController("hotel_Apartments.browse")
@UiDescriptor("apartments-browse.xml")
@LookupComponent("apartmentsTable")
@LoadDataBeforeShow
public class ApartmentsBrowse extends StandardLookup<Apartments> {
}