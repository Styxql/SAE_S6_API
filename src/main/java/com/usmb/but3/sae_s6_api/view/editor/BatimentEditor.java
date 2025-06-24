package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.function.Consumer;

import org.springframework.context.annotation.Scope;
@Scope("prototype")
@UIScope
public class BatimentEditor extends Dialog {

    public final TextField nomField = new TextField("Nom");
    public final TextField urlImgField = new TextField("URL de l'image");
    public final Button saveButton = new Button("Enregistrer", VaadinIcon.CHECK.create());
    public final Button cancelButton = new Button("Annuler");

    private final Binder<Batiment> binder = new Binder<>(Batiment.class);
    private Batiment batiment;

    public BatimentEditor(Consumer<Batiment> onSave) {
        setHeaderTitle("Bâtiment");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

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
