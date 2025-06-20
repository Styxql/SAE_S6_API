package com.usmb.but3.sae_s6_api.view;

import java.math.BigDecimal;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.service.EquipementInstalleService;
import com.usmb.but3.sae_s6_api.service.EquipementService;
import com.usmb.but3.sae_s6_api.service.MarqueService;
import com.usmb.but3.sae_s6_api.service.TypeEquipementService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class EquipementEditor extends VerticalLayout implements KeyNotifier {

    private final EquipementService equipementService;

    private Equipement equipement;

    private final TextField nom = new TextField("Nom");
    private final TextField hauteur = new TextField("Hauteur (cm)");
    private final TextField longueur = new TextField("Longueur (cm)");
    private final TextField largeur = new TextField("Largeur (cm)");

    private final ComboBox<Marque> marque = new ComboBox<>("Marque");
    private final ComboBox<TypeEquipement> typeEquipement = new ComboBox<>("Type d'équipement");

    private final Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());

    private final Binder<Equipement> binder = new Binder<>(Equipement.class);
    private ChangeHandler changeHandler;

    public EquipementEditor(
            EquipementService equipementService,
            EquipementInstalleService equipementInstalleService,
            MarqueService marqueService,
            TypeEquipementService typeEquipementService) {

        this.equipementService = equipementService;

        marque.setItems(marqueService.getAllMarques());
        marque.setItemLabelGenerator(Marque::getNom);

        typeEquipement.setItems(typeEquipementService.getAllTypeEquipements());
        typeEquipement.setItemLabelGenerator(TypeEquipement::getNom);

        HorizontalLayout generalFields = new HorizontalLayout(nom, hauteur, longueur, largeur);
        HorizontalLayout selectFields = new HorizontalLayout(marque, typeEquipement);
        HorizontalLayout actions = new HorizontalLayout(save);

        add(generalFields, selectFields, actions);
        setSpacing(true);

        binder.forField(nom)
                .asRequired("Le nom est requis")
                .bind(Equipement::getNom, Equipement::setNom);

        binder.forField(hauteur).withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Doit être un nombre décimal"))
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "La hauteur doit être strictement positive")
                .bind(Equipement::getHauteur, Equipement::setHauteur);

        binder.forField(longueur).withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Doit être un nombre décimal"))
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "La longueur doit être strictement positive")
                .bind(Equipement::getLongueur, Equipement::setLongueur);

        binder.forField(largeur).withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Doit être un nombre décimal"))
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "La largeur doit être strictement positive")
                .bind(Equipement::getLargeur, Equipement::setLargeur);
        binder.forField(marque)
                .asRequired("La marque est requise")
                .bind(Equipement::getMarque, Equipement::setMarque);

        binder.forField(typeEquipement)
                .asRequired("Le type d'équipement est requis")
                .bind(Equipement::getTypeEquipement, Equipement::setTypeEquipement);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());

        setVisible(false);
    }

    private void save() {
        try {
            binder.writeBean(equipement);
            equipementService.saveEquipement(equipement);
            changeHandler.onChange();
        } catch (ValidationException e) {
            Notification.show("Erreur : certains champs sont invalides.", 3000, Notification.Position.TOP_END);
        }
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void edit(Equipement a) {
        if (a == null) {
            setVisible(false);
            return;
        }
        equipement = (a.getId() != null)
                ? equipementService.getEquipementById(a.getId())
                : a;

        binder.readBean(equipement);
        setVisible(true);
        nom.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        this.changeHandler = h;
    }
}
