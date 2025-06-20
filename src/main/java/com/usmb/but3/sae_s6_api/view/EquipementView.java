package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.service.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("equipement")
@PageTitle("Equipements")
@Menu(title = "Equipements", order = 0, icon = "vaadin:clipboard-check")
public class EquipementView extends VerticalLayout {

    private final EquipementService equipementService;
    private final EquipementInstalleService equipementInstalleService;
    private final TypeEquipementService typeEquipementService;
    private final MarqueService marqueService;

    private final Grid<Equipement> grid = new Grid<>(Equipement.class);
    private final TextField filter = new TextField("Filtrer par nom");
    private final MultiSelectComboBox<Marque> marqueFilter = new MultiSelectComboBox<>("Marque");
    private final MultiSelectComboBox<TypeEquipement> typeFilter = new MultiSelectComboBox<>("Type");

    private final Button addNewBtn = new Button("Ajouter un équipement", VaadinIcon.PLUS.create());

    public EquipementView(
            EquipementService equipementService,
            EquipementInstalleService equipementInstalleService,
            TypeEquipementService typeEquipementService,
            MarqueService marqueService) {

        this.equipementService = equipementService;
        this.equipementInstalleService = equipementInstalleService;
        this.marqueService = marqueService;
        this.typeEquipementService = typeEquipementService;

        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listEquipement(filter.getValue()));

        marqueFilter.setItems(marqueService.getAllMarques());
        marqueFilter.setItemLabelGenerator(Marque::getNom);
        marqueFilter.addValueChangeListener(e -> listEquipement(filter.getValue()));

        typeFilter.setItems(typeEquipementService.getAllTypeEquipements());
        typeFilter.setItemLabelGenerator(TypeEquipement::getNom);
        typeFilter.addValueChangeListener(e -> listEquipement(filter.getValue()));

        HorizontalLayout actions = new HorizontalLayout(filter, marqueFilter, typeFilter, addNewBtn);
        actions.setAlignItems(Alignment.END);
        add(actions, grid);

        grid.setHeight("600px");
        grid.setColumns("id", "nom", "hauteur", "longueur", "largeur");
        grid.addColumn(e -> e.getMarque().getNom()).setHeader("Marque");
        grid.addColumn(e -> e.getTypeEquipement().getNom()).setHeader("Type");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.addComponentColumn(this::createActionButtons)
                .setHeader("Actions").setFlexGrow(0).setWidth("150px");

        grid.sort(GridSortOrder.<Equipement>asc(grid.getColumnByKey("id")).build());

        addNewBtn.addClickListener(e -> openEditDialog(new Equipement()));

        listEquipement(null);
    }

    private HorizontalLayout createActionButtons(Equipement equipement) {
        Button editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.getElement().setProperty("title", "Modifier");
        editButton.addClickListener(e -> openEditDialog(equipement));

        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addThemeName("error");
        deleteButton.getElement().setProperty("title", "Supprimer");
        deleteButton.addClickListener(e -> openDeleteDialog(equipement));

        return new HorizontalLayout(editButton, deleteButton);
    }

    private void openEditDialog(Equipement equipement) {
        EquipementEditor editor = new EquipementEditor(
                equipementService, equipementInstalleService, marqueService, typeEquipementService);

        Dialog dialog = new Dialog(editor);
        editor.edit(equipement);
        editor.setChangeHandler(() -> {
            dialog.close();
            listEquipement(filter.getValue());
            Notification.show("Équipement enregistré", 3000, Notification.Position.BOTTOM_END);
        });

        Button cancelBtn = new Button("Annuler", e -> dialog.close());
        editor.add(new HorizontalLayout(cancelBtn));

        dialog.open();
    }

    private void openDeleteDialog(Equipement equipement) {
        Dialog confirmDialog = new Dialog(new Span(
                "Confirmer la suppression de l'équipement \"" + equipement.getNom() + "\" ?"));

        Button confirmBtn = new Button("Confirmer", e -> {
            equipementService.deleteEquipementById(equipement.getId());
            confirmDialog.close();
            listEquipement(filter.getValue());
            Notification.show("Équipement supprimé", 3000, Notification.Position.TOP_END);
        });

        Button cancelBtn = new Button("Annuler", e -> confirmDialog.close());

        confirmDialog.add(new HorizontalLayout(confirmBtn, cancelBtn));
        confirmDialog.open();
    }

    private void listEquipement(String filterText) {
        var all = equipementService.getAllEquipements();

        var filtered = all.stream()
                .filter(e -> {
                    boolean matchNom = (filterText == null || filterText.isEmpty())
                            || (e.getNom() != null && e.getNom().toLowerCase().contains(filterText.toLowerCase()));

                    boolean matchMarque = marqueFilter.isEmpty()
                            || (e.getMarque() != null && marqueFilter.getValue().contains(e.getMarque()));

                    boolean matchType = typeFilter.isEmpty()
                            || (e.getTypeEquipement() != null && typeFilter.getValue().contains(e.getTypeEquipement()));

                    return matchNom && matchMarque && matchType;
                })
                .toList();

        grid.setItems(filtered);
    }
}
