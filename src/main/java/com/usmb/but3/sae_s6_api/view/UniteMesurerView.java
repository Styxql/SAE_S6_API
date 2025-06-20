package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.usmb.but3.sae_s6_api.service.UniteMesurerService;
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

@Route("unitemesurer")
@PageTitle("Unite Mesurer")
@Menu(title = "Unité Mesuré", icon = "vaadin:grid-big-o")
public class UniteMesurerView extends VerticalLayout {

    private final UniteMesurerService uniteMesurerService;

    final Grid<UniteMesurer> grid;

    public UniteMesurerView(UniteMesurerService uniteMesurerService) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de UniteMesurer");

        Button addButton = new Button("Ajouter une unité", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.uniteMesurerService = uniteMesurerService;

        this.grid = new Grid<>(UniteMesurer.class);

        grid.setColumns("id", "nom", "symbole");

        
        grid.addColumn(new ComponentRenderer<>(uniteMesurer -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();
            
            /*
             * Dialog + Form d'édition
             */
            Dialog dialogEditUniteMesurer = new Dialog();
            dialogEditUniteMesurer.setHeaderTitle("Modifier l'unité mesurer");

            // Fields
            TextField nomFieldEditUniteMesurer = new TextField("Nom");
            TextField symboleFieldEditUniteMesurer = new TextField("Symbole");

            nomFieldEditUniteMesurer.setValue(uniteMesurer.getNom());
            symboleFieldEditUniteMesurer.setValue(uniteMesurer.getSymbole());

            FormLayout formLayoutEditUniteMesurer = new FormLayout();
            formLayoutEditUniteMesurer.add(nomFieldEditUniteMesurer, symboleFieldEditUniteMesurer);
            dialogEditUniteMesurer.add(formLayoutEditUniteMesurer);

            // Bouton Save + Action de Sauvegarde
            Button saveButton = new Button("Enregistrer", event -> {
                String nomModif = nomFieldEditUniteMesurer.getValue().trim();
                String symboleModif = symboleFieldEditUniteMesurer.getValue().trim();

                // Erreur si champs vide
                if (nomModif.isEmpty() || symboleModif.isEmpty()) {
                    Notification notification = new Notification();
                    notification.setDuration(3000);

                    notification.setPosition(Notification.Position.BOTTOM_END);
                    notification.addThemeVariants(NotificationVariant.LUMO_WARNING);

                    Span text = new Span("Le nom et le symbole sont obligatoires");
                    Icon icon = VaadinIcon.WARNING.create();

                    HorizontalLayout notificationLayout = new HorizontalLayout(icon, text);
                    notificationLayout.setAlignItems(Alignment.CENTER);
                    notification.add(notificationLayout);
                    notification.open();
                    return;
                }

                // Modifie la valeur
                uniteMesurer.setNom(nomModif);
                uniteMesurer.setSymbole(symboleModif);

                // Sauvegardé Type Salle
                uniteMesurerService.saveUniteMesurer(uniteMesurer);
                Notification.show("Unité mesuré modifié avec succès");
                dialogEditUniteMesurer.close();
                refreshUniteMesurerList();
            });

            Button cancelButtonEdit = new Button("Annuler", e -> dialogEditUniteMesurer.close());
            dialogEditUniteMesurer.getFooter().add(cancelButtonEdit, saveButton);

            /*
             * Dialog Suppression
             */
            Dialog confirmDialogDelUniteMesurer = new Dialog();
            confirmDialogDelUniteMesurer.setHeaderTitle("Confirmer la suppression");

            confirmDialogDelUniteMesurer.add("Voulez-vous vraiment supprimer l'unite mesurer \"" + uniteMesurer.getNom() + "\" ?");

            Button confirmButtonDelUniteMesurer = new Button("Supprimer", event -> {
                uniteMesurerService.deleteUniteMesurerById(uniteMesurer.getId());
                Notification.show("Unite supprimé");
                confirmDialogDelUniteMesurer.close();
                refreshUniteMesurerList();
            });
            confirmButtonDelUniteMesurer.addThemeVariants(ButtonVariant.LUMO_ERROR);

            Button cancelButton = new Button("Annuler", e -> confirmDialogDelUniteMesurer.close());

            confirmDialogDelUniteMesurer.getFooter().add(cancelButton, confirmButtonDelUniteMesurer);

            add(confirmDialogDelUniteMesurer, confirmDialogDelUniteMesurer);


            /*
             * Action -> event
             */
            Button editButton = new Button(editIcon, e -> {
                dialogEditUniteMesurer.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Button deleteButton = new Button(deleteIcon, e -> {
                confirmDialogDelUniteMesurer.open();
            });
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");

        grid.setItems(this.uniteMesurerService.getAllUniteMesurers());

        add(header, grid);

        /*
         * Dialog + Form de creation
         */
        // Add new
        Dialog dialogNewUniteMesurer = new Dialog();
        dialogNewUniteMesurer.setHeaderTitle("Nouvelle unité");

        // Fields
        TextField nomFieldNewUniteMesurer = new TextField("Nom");
        TextField symboleFieldNewUniteMesurer = new TextField("Symbole");


        FormLayout formLayoutNewnUiteMesurer = new FormLayout();
        formLayoutNewnUiteMesurer.add(nomFieldNewUniteMesurer, symboleFieldNewUniteMesurer);
        dialogNewUniteMesurer.add(formLayoutNewnUiteMesurer);

        // Bouton Save + Action de Sauvegarde
        Button saveButtonUniteMesurer = new Button("Enregistrer", event -> {
            String nomUniteMesurer = nomFieldNewUniteMesurer.getValue().trim();
            String symboleUniteMesurer = symboleFieldNewUniteMesurer.getValue().trim();

            // Erreur si champs vide
            if (nomUniteMesurer.isEmpty() || symboleUniteMesurer.isEmpty()) {
                Notification notification = new Notification();
                notification.setDuration(3000);

                notification.setPosition(Notification.Position.BOTTOM_END);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);

                Span text = new Span("Le nom et le symbole sont obligatoires");
                Icon icon = VaadinIcon.WARNING.create();

                HorizontalLayout notificationLayout = new HorizontalLayout(icon, text);
                notificationLayout.setAlignItems(Alignment.CENTER);
                notification.add(notificationLayout);
                notification.open();
                return;
            }

            // Créer Type Salle
            UniteMesurer newUniteMesurer = new UniteMesurer();
            newUniteMesurer.setNom(nomUniteMesurer);
            newUniteMesurer.setSymbole(symboleUniteMesurer);

            // Sauvegardé Type Salle
            uniteMesurerService.saveUniteMesurer(newUniteMesurer);
            Notification.show("Unite ajouté avec succès");
            dialogNewUniteMesurer.close();

            refreshUniteMesurerList();
        });

        // Bouton Cancel
        Button cancelButtonNew = new Button("Annuler", e -> dialogNewUniteMesurer.close());
        dialogNewUniteMesurer.getFooter().add(cancelButtonNew, saveButtonUniteMesurer);

        // Ajout de l'event sur le bouton
        addButton.addClickListener(e -> {
            nomFieldNewUniteMesurer.clear();
            dialogNewUniteMesurer.open();
        });
    }

    private void refreshUniteMesurerList() {
        grid.setItems(uniteMesurerService.getAllUniteMesurers());
    }

}
