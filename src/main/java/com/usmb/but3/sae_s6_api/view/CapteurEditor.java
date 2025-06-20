package com.usmb.but3.sae_s6_api.view;

import java.math.BigDecimal;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.service.CapteurService;
import com.usmb.but3.sae_s6_api.service.MarqueService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class CapteurEditor extends VerticalLayout implements KeyNotifier {

    private final CapteurService capteurService;

    private Capteur capteur;

    private final TextField nom = new TextField("Nom");
    private final TextField description = new TextField("Description");
    private final TextField reference = new TextField("Référence");
    private final TextField hauteur = new TextField("Hauteur (cm)");
    private final TextField longueur = new TextField("Longueur (cm)");
    private final TextField largeur = new TextField("Largeur (cm)");
    private final TextField urlImg = new TextField("URL de l'image");

    private final ComboBox<Marque> marque = new ComboBox<>("Marque");

    private final Button save = new Button("Sauvegarder");

    private final Binder<Capteur> binder = new Binder<>(Capteur.class);
    private ChangeHandler changeHandler;

    public CapteurEditor(CapteurService capteurService, MarqueService marqueService) {
        this.capteurService = capteurService;

        marque.setItems(marqueService.getAllMarques());
        marque.setItemLabelGenerator(Marque::getNom);

        HorizontalLayout generalFields = new HorizontalLayout(nom, description, reference);
        HorizontalLayout sizeFields = new HorizontalLayout(hauteur, longueur, largeur);
        HorizontalLayout imageAndBrand = new HorizontalLayout(urlImg, marque);
        HorizontalLayout actions = new HorizontalLayout(save);

        add(generalFields, sizeFields, imageAndBrand, actions);
        setSpacing(true);

        binder.forField(nom)
                .asRequired("Le nom est requis")
                .bind(Capteur::getNom, Capteur::setNom);

        binder.forField(description)
                .bind(Capteur::getDescription, Capteur::setDescription);

        binder.forField(reference)
                .bind(Capteur::getReference, Capteur::setReference);

        binder.forField(urlImg)
                .bind(Capteur::getUrlImg, Capteur::setUrlImg);

        binder.forField(hauteur).withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Doit être un nombre décimal"))
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "Hauteur invalide")
                .bind(Capteur::getHauteur, Capteur::setHauteur);

        binder.forField(longueur).withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Doit être un nombre décimal"))
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "Longueur invalide")
                .bind(Capteur::getLongueur, Capteur::setLongueur);

        binder.forField(largeur).withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Doit être un nombre décimal"))
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "Largeur invalide")
                .bind(Capteur::getLargeur, Capteur::setLargeur);

        binder.forField(marque)
                .asRequired("La marque est requise")
                .bind(Capteur::getMarque, Capteur::setMarque);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());

        setVisible(false);
    }

    private void save() {
        try {
            binder.writeBean(capteur);
            capteurService.saveCapteur(capteur);
            if (changeHandler != null) {
                changeHandler.onChange();
            }
            setVisible(false);
        } catch (ValidationException e) {
            Notification.show("Erreur : certains champs sont invalides.", 3000, Notification.Position.TOP_END);
        }
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void edit(Capteur c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        capteur = (c.getId() != null)
                ? capteurService.getCapteurById(c.getId())
                : c;

        binder.readBean(capteur);
        setVisible(true);
        nom.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        this.changeHandler = h;
    }
}
