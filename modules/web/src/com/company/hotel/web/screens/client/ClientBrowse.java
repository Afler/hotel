package com.company.hotel.web.screens.client;

import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.Client;

@UiController("hotel_Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
@LoadDataBeforeShow
public class ClientBrowse extends StandardLookup<Client> {
}