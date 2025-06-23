package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
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

public class TypeEquipementEditor extends Dialog {

    public final TextField nomField = new TextField("Nom");
    public final Button saveButton = new Button("Enregistrer", VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button("Annuler");

    private final Binder<TypeEquipement> binder = new Binder<>(TypeEquipement.class);
    private TypeEquipement typeEquipement;

    public TypeEquipementEditor(Consumer<TypeEquipement> onSave) {
        setHeaderTitle("Type Equipement");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout form = new FormLayout(nomField);
        add(form);

        binder.forField(nomField)
                .asRequired("Le nom est obligatoire")
                .bind(TypeEquipement::getNom, TypeEquipement::setNom);

        saveButton.addClickListener(event -> {
            try {
                binder.writeBean(typeEquipement);
                onSave.accept(typeEquipement);
                close();
            } catch (ValidationException e) {
                // Vaadin va affiché automatiquement les erreurs
            }
        });

        cancelButton.addClickListener(event -> close());

        HorizontalLayout footer = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(footer);
    }

    public void editTypeEquipement(TypeEquipement typeEquipement) {
        this.typeEquipement = typeEquipement;
        binder.readBean(typeEquipement);
        setHeaderTitle(typeEquipement.getId() == null ? "Nouveau Type Equipement" : "Modifier Type Equipement");
    }
}
