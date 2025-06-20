package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.service.CapteurService;
import com.usmb.but3.sae_s6_api.service.MarqueService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;

import java.math.BigDecimal;

@Route(value = "capteur")
@PageTitle("Capteurs")
@Menu(title = "Capteurs", order = 0, icon = "vaadin:clipboard-check")
public class CapteurView extends VerticalLayout {

    private final CapteurService capteurService;
    private final MarqueService marqueService;

    private final Grid<Capteur> grid = new Grid<>(Capteur.class);
    private final TextField filter = new TextField();
    private final Button addNewBtn = new Button("Ajouter un capteur", VaadinIcon.PLUS.create());
    private final CapteurEditor editor;

    public CapteurView(CapteurService capteurService, MarqueService marqueService) {
        this.capteurService = capteurService;
        this.marqueService = marqueService;
        this.editor = new CapteurEditor(capteurService, marqueService);

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid);

        grid.setHeight("400px");
        grid.setColumns("id", "nom", "reference", "hauteur", "longueur", "largeur");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        grid.addComponentColumn(capteur -> {
            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
            editButton.addClickListener(click -> editor.edit(capteur));
            editButton.getElement().setProperty("title", "Modifier");

            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addThemeName("error");
            deleteButton.addClickListener(click -> {
                Dialog confirmDialog = new Dialog();
                confirmDialog.add(new Span("Confirmer la suppression du capteur \"" + capteur.getNom() + "\" ?"));
                Button confirmBtn = new Button("Confirmer", e -> {
                    capteurService.deleteCapteurById(capteur.getId());
                    confirmDialog.close();
                    listCapteur(filter.getValue());
                });
                Button cancelBtn = new Button("Annuler", e -> confirmDialog.close());
                confirmDialog.add(new HorizontalLayout(confirmBtn, cancelBtn));
                confirmDialog.open();
            });
            deleteButton.getElement().setProperty("title", "Supprimer");

            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Actions").setFlexGrow(0).setWidth("150px");

        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCapteur(e.getValue()));

        addNewBtn.addClickListener(e -> editor.edit(new Capteur()));

        editor.setChangeHandler(() -> {
            editor.close();
            listCapteur(filter.getValue());
        });

        listCapteur(null);
    }

    void listCapteur(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            grid.setItems(capteurService.getAllCapteurs());
        } else {
            grid.setItems(capteurService.getAllCapteurs().stream()
                    .filter(c -> c.getNom() != null && c.getNom().toLowerCase().contains(filterText.toLowerCase()))
                    .toList());
        }
    }
}


