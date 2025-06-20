package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.service.TypeEquipementService;
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

@Route("typeEquipement")
@PageTitle("TypeEquipement")
@Menu(title = "TypeEquipement", order = 0, icon = "vaadin:archives")
public class TypeEquipementView extends VerticalLayout {

    private final TypeEquipementService typeEquipementService;

    final Grid<TypeEquipement> grid;

    public TypeEquipementView(TypeEquipementService typeEquipementService) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de Type Salle");

        Button addButton = new Button("Ajouter un Type Salle", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.typeEquipementService = typeEquipementService;

        this.grid = new Grid<>(TypeEquipement.class);

        grid.setColumns("id", "nom");

        
        grid.addColumn(new ComponentRenderer<>(typeEquipement -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();
            
            /*
             * Dialog + Form d'édition
             */
            Dialog dialogEditTypeEquipement = new Dialog();
            dialogEditTypeEquipement.setHeaderTitle("Modifier le Type Salle");

            // Fields
            TextField nomFieldEditTypeEquipement = new TextField("Nom");
            nomFieldEditTypeEquipement.setValue(typeEquipement.getNom());

            FormLayout formLayoutEditTypeEquipement = new FormLayout();
            formLayoutEditTypeEquipement.add(nomFieldEditTypeEquipement);
            dialogEditTypeEquipement.add(formLayoutEditTypeEquipement);

            // Bouton Save + Action de Sauvegarde
            Button saveButton = new Button("Enregistrer", event -> {
                String nomModif = nomFieldEditTypeEquipement.getValue().trim();

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
                typeEquipement.setNom(nomModif);

                // Sauvegardé Type Salle
                typeEquipementService.saveTypeEquipement(typeEquipement);
                Notification.show("Type Salle modifié avec succès");
                dialogEditTypeEquipement.close();
                refreshTypeEquipementList();
            });

            Button cancelButtonEdit = new Button("Annuler", e -> dialogEditTypeEquipement.close());
            dialogEditTypeEquipement.getFooter().add(cancelButtonEdit, saveButton);

            /*
             * Dialog Suppression
             */
            Dialog confirmDialogDelTypeEquipement = new Dialog();
            confirmDialogDelTypeEquipement.setHeaderTitle("Confirmer la suppression");

            confirmDialogDelTypeEquipement.add("Voulez-vous vraiment supprimer le bâtiment \"" + typeEquipement.getNom() + "\" ?");

            Button confirmButtonDelTypeEquipement = new Button("Supprimer", event -> {
                typeEquipementService.deleteTypeEquipementById(typeEquipement.getId());
                Notification.show("Bâtiment supprimé");
                confirmDialogDelTypeEquipement.close();
                refreshTypeEquipementList();
            });
            confirmButtonDelTypeEquipement.addThemeVariants(ButtonVariant.LUMO_ERROR);

            Button cancelButton = new Button("Annuler", e -> confirmDialogDelTypeEquipement.close());

            confirmDialogDelTypeEquipement.getFooter().add(cancelButton, confirmButtonDelTypeEquipement);

            add(confirmDialogDelTypeEquipement, confirmDialogDelTypeEquipement);


            /*
             * Action -> event
             */
            Button editButton = new Button(editIcon, e -> {
                dialogEditTypeEquipement.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Button deleteButton = new Button(deleteIcon, e -> {
                confirmDialogDelTypeEquipement.open();
            });
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");

        grid.setItems(this.typeEquipementService.getAllTypeEquipements());

        add(header, grid);

        /*
         * Dialog + Form de creation
         */
        // Add new
        Dialog dialogNewTypeEquipement = new Dialog();
        dialogNewTypeEquipement.setHeaderTitle("Nouveau Type Salle");

        // Fields
        TextField nomFieldNewTypeEquipement = new TextField("Nom");

        FormLayout formLayoutNewTypeEquipement = new FormLayout();
        formLayoutNewTypeEquipement.add(nomFieldNewTypeEquipement); // Les fields

        dialogNewTypeEquipement.add(formLayoutNewTypeEquipement);

        // Bouton Save + Action de Sauvegarde
        Button saveButtonTypeEquipement = new Button("Enregistrer", event -> {
            String nomTypeEquipement = nomFieldNewTypeEquipement.getValue().trim();

            // Erreur si champs vide
            if (nomTypeEquipement.isEmpty()) {
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
            TypeEquipement newTypeEquipement = new TypeEquipement();
            newTypeEquipement.setNom(nomTypeEquipement);

            // Sauvegardé Type Salle
            typeEquipementService.saveTypeEquipement(newTypeEquipement);
            Notification.show("Type Salle ajouté avec succès");
            dialogNewTypeEquipement.close();

            refreshTypeEquipementList();
        });

        // Bouton Cancel
        Button cancelButtonNew = new Button("Annuler", e -> dialogNewTypeEquipement.close());
        dialogNewTypeEquipement.getFooter().add(cancelButtonNew, saveButtonTypeEquipement);

        // Ajout de l'event sur le bouton
        addButton.addClickListener(e -> {
            nomFieldNewTypeEquipement.clear();
            dialogNewTypeEquipement.open();
        });
    }

    private void refreshTypeEquipementList() {
        grid.setItems(typeEquipementService.getAllTypeEquipements());
    }

}
