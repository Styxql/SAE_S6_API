package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.function.Consumer;

public class UniteMesurerEditor extends Dialog {

    TextField nomField = new TextField("Nom");
    TextField symboleField = new TextField("Symbole");

    private final Button saveButton = new Button("Enregistrer");
    private final Button cancelButton = new Button("Annuler");

    private final Binder<UniteMesurer> binder = new Binder<>(UniteMesurer.class);
    private UniteMesurer uniteMesurer;

    public UniteMesurerEditor(Consumer<UniteMesurer> onSave) {
        setHeaderTitle("Unite Mesurer");

        FormLayout formLayout = new FormLayout();
        formLayout.add(nomField, symboleField);
        add(formLayout);

        HorizontalLayout footer = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(footer);

        binder.forField(nomField)
            .asRequired("Le nom est obligatoire")
            .bind(UniteMesurer::getNom, UniteMesurer::setNom);

        binder.forField(symboleField)
            .bind(UniteMesurer::getSymbole, UniteMesurer::setSymbole);

        saveButton.addClickListener(e -> {
            try {
                binder.writeBean(uniteMesurer);
                onSave.accept(uniteMesurer);
                close();
            } catch (ValidationException ex) {
                // Vaadin va affiché automatiquement les erreurs
            }
        });

        cancelButton.addClickListener(e -> close());
    }

    public void editUniteMesurer(UniteMesurer uniteMesurer) {
        this.uniteMesurer = uniteMesurer;
        binder.readBean(uniteMesurer);
        setHeaderTitle(uniteMesurer.getId() == null ? "Nouveau Unite Mesurer" : "Modifier Unite Mesurer");
    }
}
