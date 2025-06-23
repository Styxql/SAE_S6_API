package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.Marque;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.function.Consumer;

public class MarqueEditor extends Dialog {

    private final TextField nomField = new TextField("Nom");
    private final Button saveButton = new Button("Enregistrer", VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button("Annuler");

    private final Binder<Marque> binder = new Binder<>(Marque.class);
    private Marque marque;

    public MarqueEditor(Consumer<Marque> onSave) {
        setHeaderTitle("Marque");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout form = new FormLayout(nomField);
        add(form);

        binder.forField(nomField)
                .asRequired("Le nom est obligatoire")
                .bind(Marque::getNom, Marque::setNom);

        saveButton.addClickListener(event -> {
            try {
                binder.writeBean(marque);
                onSave.accept(marque);
                close();
            } catch (ValidationException e) {
                // Vaadin va affiché automatiquement les erreurs
            }
        });

        cancelButton.addClickListener(event -> close());

        HorizontalLayout footer = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(footer);
    }

    public void editMarque(Marque marque) {
        this.marque = marque;
        binder.readBean(marque);
        setHeaderTitle(marque.getId() == null ? "Nouvelle Marque" : "Modifier Marque");
    }
}
