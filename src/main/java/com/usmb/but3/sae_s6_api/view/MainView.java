package com.usmb.but3.sae_s6_api.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
    public MainView() {
        add(new Button("Cliquez-moi", e -> Notification.show("Vaadin + Gradle !")));
    }
}
