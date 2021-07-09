package com.company.hotel.web.screens.registrationcard;

import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.RegistrationCard;

@UiController("hotel_RegistrationCard.browse")
@UiDescriptor("registration-card-browse.xml")
@LookupComponent("registrationCardsTable")
@LoadDataBeforeShow
public class RegistrationCardBrowse extends StandardLookup<RegistrationCard> {
}