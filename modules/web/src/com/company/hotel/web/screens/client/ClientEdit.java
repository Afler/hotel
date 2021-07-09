package com.company.hotel.web.screens.client;

import com.haulmont.cuba.gui.screen.*;
import com.company.hotel.entity.Client;

@UiController("hotel_Client.edit")
@UiDescriptor("client-edit.xml")
@EditedEntityContainer("clientDc")
@LoadDataBeforeShow
public class ClientEdit extends StandardEditor<Client> {
}