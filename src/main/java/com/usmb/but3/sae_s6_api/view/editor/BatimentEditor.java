package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.function.Consumer;

public class BatimentEditor extends Dialog {

    private final TextField nomField = new TextField("Nom");
    private final TextField urlImgField = new TextField("URL de l'image");
    private final Button saveButton = new Button("Enregistrer");
    private final Button cancelButton = new Button("Annuler");

    private final Binder<Batiment> binder = new Binder<>(Batiment.class);
    private Batiment batiment;

    public BatimentEditor(Consumer<Batiment> onSave) {
        setHeaderTitle("Bâtiment");

        FormLayout formLayout = new FormLayout();
        formLayout.add(nomField, urlImgField);
        add(formLayout);

        HorizontalLayout buttons = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(buttons);

        // Bind fields
        binder.forField(nomField)
                .asRequired("Le nom est obligatoire")
                .bind(Batiment::getNom, Batiment::setNom);

        binder.forField(urlImgField)
                .bind(Batiment::getUrlImg, Batiment::setUrlImg);

        saveButton.addClickListener(e -> {
            try {
                binder.writeBean(batiment);
                onSave.accept(batiment);
                close();
            } catch (ValidationException ex) {
                // Vaadin va affiché automatiquement les erreurs
            }
        });

        cancelButton.addClickListener(e -> close());
    }

    public void editBatiment(Batiment batiment) {
        this.batiment = batiment;
        setHeaderTitle(batiment.getId() == null ? "Nouveau bâtiment" : "Modifier le bâtiment");
        binder.readBean(batiment);
    }
}
