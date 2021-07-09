package com.company.hotel.web.screens.apartments;

import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.Apartments;

@UiController("hotel_Apartments.edit")
@UiDescriptor("apartments-edit.xml")
@EditedEntityContainer("apartmentsDc")
@LoadDataBeforeShow
public class ApartmentsEdit extends StandardEditor<Apartments> {
}