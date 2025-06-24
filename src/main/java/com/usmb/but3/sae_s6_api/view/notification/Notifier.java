package com.usmb.but3.sae_s6_api.view.notification;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

public class Notifier {
    public static void show(String nom, NotificationType notificationType) {

        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_END);
        String messageNotification;
        Icon icon; 

        switch (notificationType) {
            case SUCCES_ADD:
                messageNotification = String.format("\"%s\" a été ajouté avec succès", nom);
                icon = VaadinIcon.CHECK.create();
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                break;

            case SUCCES_REMOVE:
                messageNotification = String.format("\"%s\" a été retiré avec succès", nom);
                icon = VaadinIcon.CHECK.create();
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                break;

            case SUCCES_DELETE:
                messageNotification = String.format("\"%s\" a été supprimé avec succès", nom);
                icon = VaadinIcon.CHECK.create();
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                break;
            
            case SUCCES_NEW:
                messageNotification = String.format("\"%s\" a été créé avec succès", nom);
                icon = VaadinIcon.CHECK.create();
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                break;

            case SUCCES_EDIT:
                messageNotification = String.format("\"%s\" a été modifié avec succès", nom);
                icon = VaadinIcon.CHECK.create();
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                break;

            case ERROR_NEW:
                messageNotification = String.format("Une erreur est survenue lors de la tentative de création de \"%s\"", nom);
                icon = VaadinIcon.WARNING.create();
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                break;
            
            case ERROR_EDIT:
                messageNotification = String.format("Une erreur est survenue lors de la tentative de modification de \"%s\"", nom);
                icon = VaadinIcon.WARNING.create();
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                break;

            case ERROR_DELETE:
                messageNotification = String.format("Une erreur est survenue lors de la tentative de suppresion de \"%s\"", nom);
                icon = VaadinIcon.WARNING.create();
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                break;
            
            default:
                icon = VaadinIcon.WARNING.create();
                messageNotification = "";
        }
  
        HorizontalLayout notificationLayout = new HorizontalLayout(icon, new Span(messageNotification));
        notificationLayout.setAlignItems(Alignment.CENTER);

        notification.add(notificationLayout);
        
        notification.setDuration(3000);
        notification.open();
        return;
    }
}

