package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.service.TypeSalleService;
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

@Route("typesalle")
@PageTitle("Type Salle")
@Menu(title = "TypeSalle", icon = "vaadin:grid-big-o")
public class TypeSalleView extends VerticalLayout {

    private final TypeSalleService typeSalleService;

    final Grid<TypeSalle> grid;

    public TypeSalleView(TypeSalleService typeSalleService) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de Type Salle");

        Button addButton = new Button("Ajouter un Type Salle", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.typeSalleService = typeSalleService;

        this.grid = new Grid<>(TypeSalle.class);

        grid.setColumns("id", "nom");

        
        grid.addColumn(new ComponentRenderer<>(typesalle -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();
            
            /*
             * Dialog + Form d'édition
             */
            Dialog dialogEditTypeSalle = new Dialog();
            dialogEditTypeSalle.setHeaderTitle("Modifier le Type Salle");

            // Fields
            TextField nomFieldEditTypeSalle = new TextField("Nom");
            nomFieldEditTypeSalle.setValue(typesalle.getNom());

            FormLayout formLayoutEditTypeSalle = new FormLayout();
            formLayoutEditTypeSalle.add(nomFieldEditTypeSalle);
            dialogEditTypeSalle.add(formLayoutEditTypeSalle);

            // Bouton Save + Action de Sauvegarde
            Button saveButton = new Button("Enregistrer", event -> {
                String nomModif = nomFieldEditTypeSalle.getValue().trim();

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
                typesalle.setNom(nomModif);

                // Sauvegardé Type Salle
                typeSalleService.saveTypeSalle(typesalle);
                Notification.show("Type Salle modifié avec succès");
                dialogEditTypeSalle.close();
                refreshTypeSalleList();
            });

            Button cancelButtonEdit = new Button("Annuler", e -> dialogEditTypeSalle.close());
            dialogEditTypeSalle.getFooter().add(cancelButtonEdit, saveButton);

            /*
             * Dialog Suppression
             */
            Dialog confirmDialogDelTypeSalle = new Dialog();
            confirmDialogDelTypeSalle.setHeaderTitle("Confirmer la suppression");

            confirmDialogDelTypeSalle.add("Voulez-vous vraiment supprimer le bâtiment \"" + typesalle.getNom() + "\" ?");

            Button confirmButtonDelTypeSalle = new Button("Supprimer", event -> {
                typeSalleService.deleteTypeSalleById(typesalle.getId());
                Notification.show("Bâtiment supprimé");
                confirmDialogDelTypeSalle.close();
                refreshTypeSalleList();
            });
            confirmButtonDelTypeSalle.addThemeVariants(ButtonVariant.LUMO_ERROR);

            Button cancelButton = new Button("Annuler", e -> confirmDialogDelTypeSalle.close());

            confirmDialogDelTypeSalle.getFooter().add(cancelButton, confirmButtonDelTypeSalle);

            add(confirmDialogDelTypeSalle, confirmDialogDelTypeSalle);


            /*
             * Action -> event
             */
            Button editButton = new Button(editIcon, e -> {
                dialogEditTypeSalle.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Button deleteButton = new Button(deleteIcon, e -> {
                confirmDialogDelTypeSalle.open();
            });
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");

        grid.setItems(this.typeSalleService.getAllTypeSalles());

        add(header, grid);

        /*
         * Dialog + Form de creation
         */
        // Add new
        Dialog dialogNewTypeSalle = new Dialog();
        dialogNewTypeSalle.setHeaderTitle("Nouveau Type Salle");

        // Fields
        TextField nomFieldNewTypeSalle = new TextField("Nom");

        FormLayout formLayoutNewTypeSalle = new FormLayout();
        formLayoutNewTypeSalle.add(nomFieldNewTypeSalle); // Les fields

        dialogNewTypeSalle.add(formLayoutNewTypeSalle);

        // Bouton Save + Action de Sauvegarde
        Button saveButtonTypeSalle = new Button("Enregistrer", event -> {
            String nomTypeSalle = nomFieldNewTypeSalle.getValue().trim();

            // Erreur si champs vide
            if (nomTypeSalle.isEmpty()) {
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
            TypeSalle newTypeSalle = new TypeSalle();
            newTypeSalle.setNom(nomTypeSalle);

            // Sauvegardé Type Salle
            typeSalleService.saveTypeSalle(newTypeSalle);
            Notification.show("Type Salle ajouté avec succès");
            dialogNewTypeSalle.close();

            refreshTypeSalleList();
        });

        // Bouton Cancel
        Button cancelButtonNew = new Button("Annuler", e -> dialogNewTypeSalle.close());
        dialogNewTypeSalle.getFooter().add(cancelButtonNew, saveButtonTypeSalle);

        // Ajout de l'event sur le bouton
        addButton.addClickListener(e -> {
            nomFieldNewTypeSalle.clear();
            dialogNewTypeSalle.open();
        });
    }

    private void refreshTypeSalleList() {
        grid.setItems(typeSalleService.getAllTypeSalles());
    }

}
