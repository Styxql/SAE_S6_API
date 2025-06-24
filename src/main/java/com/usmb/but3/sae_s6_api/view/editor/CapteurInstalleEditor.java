package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
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

public class CapteurInstalleEditor extends Dialog {

    private final ComboBox<Capteur> capteurField = new ComboBox<>("Capteur");
    private final IntegerField nombreField = new IntegerField("Nombre d'Capteur");
    
    private final Button saveButton = new Button("Enregistrer", VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button("Annuler");

    private final Binder<CapteurInstalle> binder = new Binder<>(CapteurInstalle.class);
    private CapteurInstalle capteurInstalle;

    public CapteurInstalleEditor(Consumer<CapteurInstalle> onSave, List<Capteur> capteurs, Salle salle) {
        capteurField.setItems(capteurs);
        capteurField.setItemLabelGenerator(Capteur::getNom);

        setHeaderTitle("Capteur Installe");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout();
        formLayout.add(capteurField, nombreField);
        add(formLayout);

        binder.forField(capteurField)
                .asRequired("Le capteur est obligatoire")
                .bind(CapteurInstalle::getCapteur, CapteurInstalle::setCapteur);

        binder.forField(nombreField)
                .asRequired("Le nombre de Capteur est obligatoire")
                .bind(CapteurInstalle::getNombre, CapteurInstalle::setNombre);

        saveButton.addClickListener(event -> {
            try {
                capteurInstalle.setSalle(salle);
                binder.writeBean(capteurInstalle);
                onSave.accept(capteurInstalle);
                close();
            } catch (ValidationException e) {
                // Vaadin va affiché automatiquement les erreurs
            }
        });

        cancelButton.addClickListener(event -> close());

        HorizontalLayout footer = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(footer);
    }

    public void editCapteurInstalle(CapteurInstalle capteurInstalle) {
        this.capteurInstalle = capteurInstalle;
        binder.readBean(capteurInstalle);
        setHeaderTitle(capteurInstalle.getId() == null ? "Nouvelle Marque" : "Modifier Marque");
    }
}
