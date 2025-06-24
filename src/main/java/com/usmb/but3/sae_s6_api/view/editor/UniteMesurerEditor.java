package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
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
public class UniteMesurerEditor extends Dialog {

    public TextField nomField = new TextField("Nom");
    public TextField symboleField = new TextField("Symbole");

    public final Button saveButton = new Button("Enregistrer", VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button("Annuler");

    private final Binder<UniteMesurer> binder = new Binder<>(UniteMesurer.class);
    private UniteMesurer uniteMesurer;

    public UniteMesurerEditor(Consumer<UniteMesurer> onSave) {
        setHeaderTitle("Unite Mesurer");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

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
