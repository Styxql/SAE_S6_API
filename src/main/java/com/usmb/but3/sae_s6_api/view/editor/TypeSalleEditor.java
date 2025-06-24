package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.vaadin.flow.component.button.Button;
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

public class TypeSalleEditor extends Dialog {

    public final TextField nomField = new TextField("Nom");
    public final Button saveButton = new Button("Enregistrer", VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button("Annuler");

    private final Binder<TypeSalle> binder = new Binder<>(TypeSalle.class);
    private TypeSalle typeSalle;

    

    public TypeSalleEditor(Consumer<TypeSalle> onSave) {
        setHeaderTitle("Type de Salle");

        FormLayout formLayout = new FormLayout();
        formLayout.add(nomField);
        add(formLayout);

        HorizontalLayout footer = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(footer);

        binder.forField(nomField)
                .asRequired("Le nom est obligatoire")
                .bind(TypeSalle::getNom, TypeSalle::setNom);

        saveButton.addClickListener(e -> {
            try {
                binder.writeBean(typeSalle);
                onSave.accept(typeSalle);
                close();
            } catch (ValidationException ex) {
                // Vaadin va affiché automatiquement les erreurs
            }
        });

        cancelButton.addClickListener(e -> close());
    }

    public void editTypeSalle(TypeSalle typeSalle) {
        this.typeSalle = typeSalle;
        binder.readBean(typeSalle);
        setHeaderTitle(typeSalle.getId() == null ? "Nouveau Type de Salle" : "Modifier Type de Salle");
    }
}
