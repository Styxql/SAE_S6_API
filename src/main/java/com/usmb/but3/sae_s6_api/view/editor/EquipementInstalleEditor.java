package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.List;
import java.util.function.Consumer;

public class EquipementInstalleEditor extends Dialog {

    private final IntegerField nombreField = new IntegerField("Nombre");
    private final ComboBox<Equipement> equipementCombo = new ComboBox<>("Équipement");
    private final ComboBox<Salle> salleCombo = new ComboBox<>("Salle");
    private final Button saveButton = new Button("Enregistrer");
    private final Button cancelButton = new Button("Annuler");

    private final Binder<EquipementInstalle> binder = new Binder<>(EquipementInstalle.class);
    private EquipementInstalle equipementInstalle;

    public EquipementInstalleEditor(List<Equipement> equipements, List<Salle> salles, Consumer<EquipementInstalle> onSave) {
        setHeaderTitle("Équipement installé");

        // Configuration des ComboBox
        equipementCombo.setItems(equipements);
        equipementCombo.setItemLabelGenerator(Equipement::toString); // à ajuster selon toString()
        
        salleCombo.setItems(salles);
        salleCombo.setItemLabelGenerator(Salle::toString); // à ajuster selon toString()

        FormLayout formLayout = new FormLayout();
        formLayout.add(nombreField, equipementCombo, salleCombo);
        add(formLayout);

        HorizontalLayout buttons = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(buttons);

        // Binding
        binder.forField(nombreField)
                .asRequired("Le nombre est obligatoire")
                .bind(EquipementInstalle::getNombre, EquipementInstalle::setNombre);

        binder.forField(equipementCombo)
                .asRequired("L’équipement est requis")
                .bind(EquipementInstalle::getEquipement, EquipementInstalle::setEquipement);

        binder.forField(salleCombo)
                .asRequired("La salle est requise")
                .bind(EquipementInstalle::getSalle, EquipementInstalle::setSalle);

        saveButton.addClickListener(e -> {
            try {
                binder.writeBean(equipementInstalle);
                onSave.accept(equipementInstalle);
                close();
            } catch (ValidationException ex) {
                // Vaadin gère automatiquement les erreurs de validation
            }
        });

        cancelButton.addClickListener(e -> close());
    }

    public void editEquipementInstalle(EquipementInstalle equipementInstalle) {
        this.equipementInstalle = equipementInstalle;
        setHeaderTitle(equipementInstalle.getId() == null ? "Nouvel équipement installé" : "Modifier équipement installé");
        binder.readBean(equipementInstalle);
    }
}
