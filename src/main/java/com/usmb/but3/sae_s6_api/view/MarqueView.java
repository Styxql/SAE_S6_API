package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.service.MarqueService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("marque")
@PageTitle("Marque")
@Menu(title = "Marque", order = 0, icon = "vaadin:archive")
public class MarqueView extends VerticalLayout {

    private final MarqueService marqueService;

    final Grid<Marque> grid;

    public MarqueView(MarqueService marqueService) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de Type Salle");

        Button addButton = new Button("Ajouter un Type Salle", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.marqueService = marqueService;

        this.grid = new Grid<>(Marque.class);

        grid.setColumns("id", "nom");

        
        grid.addColumn(new ComponentRenderer<>(marque -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();
            
            /*
             * Dialog + Form d'édition
             */
            Dialog dialogEditMarque = new Dialog();
            dialogEditMarque.setHeaderTitle("Modifier le Type Salle");

            // Fields
            TextField nomFieldEditMarque = new TextField("Nom");
            nomFieldEditMarque.setValue(marque.getNom());

            FormLayout formLayoutEditMarque = new FormLayout();
            formLayoutEditMarque.add(nomFieldEditMarque);
            dialogEditMarque.add(formLayoutEditMarque);

            // Bouton Save + Action de Sauvegarde
            Button saveButton = new Button("Enregistrer", event -> {
                String nomModif = nomFieldEditMarque.getValue().trim();

                // Erreur si champs vide
                if (nomModif.isEmpty()) {
                    Notification notification = new Notification();
                    notification.setDuration(3000);
                    notification.setPosition(Notification.Position.BOTTOM_END);

                    notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                    Span text = new Span("Le nom est obligatoire");
                    Icon icon = VaadinIcon.WARNING.create();

                    HorizontalLayout notificationLayout = new HorizontalLayout(icon, text);
                    notificationLayout.setAlignItems(Alignment.CENTER);

                    notification.add(notificationLayout);
                    notification.open();
                    return;
                }

                // Modifie la valeur
                marque.setNom(nomModif);

                // Sauvegardé Type Salle
                marqueService.saveMarque(marque);
                Notification.show("Type Salle modifié avec succès");
                dialogEditMarque.close();
                refreshMarqueList();
            });

            Button cancelButtonEdit = new Button("Annuler", e -> dialogEditMarque.close());
            dialogEditMarque.getFooter().add(cancelButtonEdit, saveButton);

            /*
             * Dialog Suppression
             */
            Dialog confirmDialogDelMarque = new Dialog();
            confirmDialogDelMarque.setHeaderTitle("Confirmer la suppression");

            confirmDialogDelMarque.add("Voulez-vous vraiment supprimer le bâtiment \"" + marque.getNom() + "\" ?");

            Button confirmButtonDelMarque = new Button("Supprimer", event -> {
                marqueService.deleteMarqueById(marque.getId());
                Notification.show("Bâtiment supprimé");
                confirmDialogDelMarque.close();
                refreshMarqueList();
            });
            confirmButtonDelMarque.addThemeVariants(ButtonVariant.LUMO_ERROR);

            Button cancelButton = new Button("Annuler", e -> confirmDialogDelMarque.close());

            confirmDialogDelMarque.getFooter().add(cancelButton, confirmButtonDelMarque);

            add(confirmDialogDelMarque, confirmDialogDelMarque);


            /*
             * Action -> event
             */
            Button editButton = new Button(editIcon, e -> {
                dialogEditMarque.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Button deleteButton = new Button(deleteIcon, e -> {
                confirmDialogDelMarque.open();
            });
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");

        grid.setItems(this.marqueService.getAllMarques());

        add(header, grid);

        /*
         * Dialog + Form de creation
         */
        // Add new
        Dialog dialogNewMarque = new Dialog();
        dialogNewMarque.setHeaderTitle("Nouveau Type Salle");

        // Fields
        TextField nomFieldNewMarque = new TextField("Nom");

        FormLayout formLayoutNewMarque = new FormLayout();
        formLayoutNewMarque.add(nomFieldNewMarque); // Les fields

        dialogNewMarque.add(formLayoutNewMarque);

        // Bouton Save + Action de Sauvegarde
        Button saveButtonMarque = new Button("Enregistrer", event -> {
            String nomMarque = nomFieldNewMarque.getValue().trim();

            // Erreur si champs vide
            if (nomMarque.isEmpty()) {
                Notification notification = new Notification();
                notification.setDuration(3000);
                notification.setPosition(Notification.Position.BOTTOM_END);

                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                Span text = new Span("Le nom est obligatoire");
                Icon icon = VaadinIcon.WARNING.create();

                HorizontalLayout notificationLayout = new HorizontalLayout(icon, text);
                notificationLayout.setAlignItems(Alignment.CENTER);

                notification.add(notificationLayout);
                notification.open();
                return;
            }

            // Créer Type Salle
            Marque newMarque = new Marque();
            newMarque.setNom(nomMarque);

            // Sauvegardé Type Salle
            marqueService.saveMarque(newMarque);
            Notification.show("Type Salle ajouté avec succès");
            dialogNewMarque.close();

            refreshMarqueList();
        });

        // Bouton Cancel
        Button cancelButtonNew = new Button("Annuler", e -> dialogNewMarque.close());
        dialogNewMarque.getFooter().add(cancelButtonNew, saveButtonMarque);

        // Ajout de l'event sur le bouton
        addButton.addClickListener(e -> {
            nomFieldNewMarque.clear();
            dialogNewMarque.open();
        });
    }

    private void refreshMarqueList() {
        grid.setItems(marqueService.getAllMarques());
    }

}
