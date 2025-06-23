package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.service.TypeEquipementService;
import com.usmb.but3.sae_s6_api.view.editor.TypeEquipementEditor;
import com.usmb.but3.sae_s6_api.view.notification.NotificationType;
import com.usmb.but3.sae_s6_api.view.notification.Notifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("typeequipement")
@PageTitle("Typeequipement")
public class TypeEquipementView extends VerticalLayout {

    private final TypeEquipementService typeEquipementService;

    final Grid<TypeEquipement> grid;

    public TypeEquipementView(TypeEquipementService typeEquipementService) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de Type Equipement");

        Button addButton = new Button("Ajouter un Type Equipement", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.typeEquipementService = typeEquipementService;

        this.grid = new Grid<>(TypeEquipement.class);

        grid.setColumns("id", "nom");

        
        grid.addColumn(new ComponentRenderer<>(typeEquipement -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();
            
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
                TypeEquipementEditor editForm = new TypeEquipementEditor(typeequipementModif -> {
                    typeEquipementService.saveTypeEquipement(typeequipementModif);
                    Notifier.show(typeequipementModif.getNom(), NotificationType.SUCCES_EDIT);
                    refreshTypeEquipementList();
                });
                editForm.editTypeEquipement(typeEquipement);
                add(editForm);
                editForm.open();
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

        TypeEquipementEditor editor = new TypeEquipementEditor(typeequipement -> {
            typeEquipementService.saveTypeEquipement(typeequipement);
            Notifier.show(typeequipement.getNom(), NotificationType.SUCCES_NEW);
            refreshTypeEquipementList();
        });

        addButton.addClickListener(e -> {
            editor.editTypeEquipement(new TypeEquipement());
            add(editor);
            editor.open();
        });
    }

    private void refreshTypeEquipementList() {
        grid.setItems(typeEquipementService.getAllTypeEquipements());
    }

}
