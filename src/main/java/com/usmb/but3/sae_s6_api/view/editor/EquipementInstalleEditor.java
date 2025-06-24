package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.List;
import java.util.function.Consumer;

public class EquipementInstalleEditor extends Dialog {

    private final ComboBox<Equipement> equipementField = new ComboBox<>("Equipement");
    private final IntegerField nombreField = new IntegerField("Nombre d'Equipement");
    
    private final Button saveButton = new Button("Enregistrer", VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button("Annuler");

    private final Binder<EquipementInstalle> binder = new Binder<>(EquipementInstalle.class);
    private EquipementInstalle equipementInstalle;

    public EquipementInstalleEditor(Consumer<EquipementInstalle> onSave, List<Equipement> equipements, Salle salle) {
        equipementField.setItems(equipements);
        equipementField.setItemLabelGenerator(Equipement::getNom);

        setHeaderTitle("Equipement Installe");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout();
        formLayout.add(equipementField, nombreField);
        add(formLayout);

        binder.forField(equipementField)
                .asRequired("Le equipement est obligatoire")
                .bind(EquipementInstalle::getEquipement, EquipementInstalle::setEquipement);

        binder.forField(nombreField)
                .asRequired("Le nombre d'Equipement est obligatoire")
                .bind(EquipementInstalle::getNombre, EquipementInstalle::setNombre);

        saveButton.addClickListener(event -> {
            try {
                equipementInstalle.setSalle(salle);
                binder.writeBean(equipementInstalle);
                onSave.accept(equipementInstalle);
                close();
            } catch (ValidationException e) {
                // Vaadin va affiché automatiquement les erreurs
            }
        });

        cancelButton.addClickListener(event -> close());

        HorizontalLayout footer = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(footer);
    }

    public void editEquipementInstalle(EquipementInstalle equipementInstalle) {
        this.equipementInstalle = equipementInstalle;
        binder.readBean(equipementInstalle);
        setHeaderTitle(equipementInstalle.getId() == null ? "Nouvelle Marque" : "Modifier Marque");
    }
}
