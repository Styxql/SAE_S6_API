package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.service.EquipementService;
import com.usmb.but3.sae_s6_api.service.EquipementInstalleService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("equipement")
@PageTitle("Equipements")
public class EquipementView extends VerticalLayout {

    private final EquipementService equipementService;
    private final EquipementInstalleService equipementInstalleService;

    private final Grid<Equipement> grid = new Grid<>(Equipement.class);
    private final TextField filter = new TextField();
    private final Button addNewBtn = new Button("Ajouter un équipement", VaadinIcon.PLUS.create());

    public EquipementView(EquipementService equipementService, EquipementInstalleService equipementInstalleService) {
        this.equipementService = equipementService;
        this.equipementInstalleService = equipementInstalleService;

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid);

        grid.setHeight("400px");
        grid.setColumns("id", "nom", "hauteur", "longueur", "largeur");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        grid.addComponentColumn(equipement -> {
            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
            editButton.getElement().setProperty("title", "Modifier");

            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addThemeName("error");
            deleteButton.getElement().setProperty("title", "Supprimer");

            editButton.addClickListener(e -> openEditDialog(new EquipementEditor(equipementService, equipementInstalleService), equipement));

            deleteButton.addClickListener(click -> {
                Dialog confirmDialog = new Dialog();
                confirmDialog.add(new Span("Confirmer la suppression de l’équipement \"" + equipement.getNom() + "\" ?"));
                Button confirmBtn = new Button("Confirmer", ev -> {
                    equipementService.deleteEquipementById(equipement.getId());
                    confirmDialog.close();
                    listEquipement(filter.getValue());
                    Notification.show("Équipement supprimé", 3000, Notification.Position.MIDDLE);
                });
                Button cancelBtn = new Button("Annuler", ev -> confirmDialog.close());
                confirmDialog.add(new HorizontalLayout(confirmBtn, cancelBtn));
                confirmDialog.open();
            });

            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Actions").setFlexGrow(0).setWidth("150px");

        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listEquipement(e.getValue()));

        addNewBtn.addClickListener(e ->
                openEditDialog(new EquipementEditor(equipementService, equipementInstalleService), new Equipement())
        );

        listEquipement(null);
    }

    private void openEditDialog(EquipementEditor editor, Equipement equipement) {
        Dialog dialog = new Dialog(editor);
        editor.edit(equipement);
        editor.setChangeHandler(() -> {
            dialog.close();
            listEquipement(filter.getValue());
            Notification.show("Équipement enregistré", 3000, Notification.Position.BOTTOM_END);
        });
        dialog.open();
    }

    void listEquipement(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            grid.setItems(equipementService.getAllEquipements());
        } else {
            grid.setItems(equipementService.getAllEquipements().stream()
                    .filter(e -> e.getNom() != null && e.getNom().toLowerCase().contains(filterText.toLowerCase()))
                    .toList());
        }
    }
}
