package com.usmb.but3.sae_s6_api.view.editor;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.util.List;
import java.util.function.Consumer;

public class SalleEditor extends Dialog {

    private final TextField nomField = new TextField("Nom");
    private final TextField urlImgField = new TextField("URL de l'image");
    private final IntegerField capaciteField = new IntegerField("Capacité");
    private final ComboBox<TypeSalle> typeSalleField = new ComboBox<>("Type de salle");

    private final Button saveButton = new Button("Enregistrer", VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button("Annuler");

    private final Binder<Salle> binder = new Binder<>(Salle.class);
    private Salle salle;

    public SalleEditor(Consumer<Salle> onSave, List<TypeSalle> typeSalles, Batiment batiment) {
        typeSalleField.setItems(typeSalles);
        typeSalleField.setItemLabelGenerator(TypeSalle::getNom);

        setHeaderTitle("Salle");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout();
        formLayout.add(nomField, urlImgField, capaciteField, typeSalleField);
        add(formLayout);

        HorizontalLayout buttons = new HorizontalLayout(cancelButton, saveButton);
        getFooter().add(buttons);

        // Bind fields
        binder.forField(nomField)
                .asRequired("Le nom est obligatoire")
                .withValidator(new StringLengthValidator("Minimum 2 caractères", 2, null))
                .bind(Salle::getNom, Salle::setNom);

        binder.forField(urlImgField)
                .bind(Salle::getUrlImg, Salle::setUrlImg);

        binder.forField(capaciteField)
                .asRequired("La capacité est obligatoire")
                .bind(Salle::getCapacite, Salle::setCapacite);

        binder.forField(typeSalleField)
                .asRequired("Le type de salle est obligatoire")
                .bind(Salle::getTypeSalle, Salle::setTypeSalle);

        saveButton.addClickListener(e -> {
            try {
                salle.setBatiment(batiment);
                binder.writeBean(salle);
                onSave.accept(salle);
                close();
            } catch (ValidationException ex) {
                // Vaadin va affiché automatiquement les erreurs
            }
        });
        
        cancelButton.addClickListener(e -> close());
    }

    public void editSalle(Salle salle) {
        this.salle = salle;
        setHeaderTitle(salle.getId() == null ? "Nouvelle salle" : "Modifier la salle");
        binder.readBean(salle);
    }

}
