package com.usmb.but3.sae_s6_api.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
    public MainView() {
UI.getCurrent().getPage().setLocation("batiment");    }
}
