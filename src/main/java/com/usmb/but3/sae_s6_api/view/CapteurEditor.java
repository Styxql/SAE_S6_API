package com.usmb.but3.sae_s6_api.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.entity.ParametreCapteur;
import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.usmb.but3.sae_s6_api.service.CapteurService;
import com.usmb.but3.sae_s6_api.service.MarqueService;
import com.usmb.but3.sae_s6_api.service.UniteMesurerService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class CapteurEditor extends VerticalLayout implements KeyNotifier {

        private final CapteurService capteurService;
        private final MarqueService marqueService;
        private final UniteMesurerService uniteMesurerService;

        private Capteur capteur;

        private final TextField nom = new TextField("Nom");
        private final TextArea description = new TextArea("Description");
        private final TextField reference = new TextField("Référence");
        private final TextField hauteur = new TextField("Hauteur (cm)");
        private final TextField longueur = new TextField("Longueur (cm)");
        private final TextField largeur = new TextField("Largeur (cm)");

        private final ComboBox<Marque> marque = new ComboBox<>("Marque");
        private final VerticalLayout parametreListLayout = new VerticalLayout();
        private final Button addParametreButton = new Button("Ajouter un paramètre");

        private final Button save = new Button("Sauvegarder");

        private final Binder<Capteur> binder = new Binder<>(Capteur.class);

        private final List<Binder<ParametreCapteur>> parametreBinders = new ArrayList<>();

        private ChangeHandler changeHandler;

        public CapteurEditor(CapteurService capteurService, MarqueService marqueService,
                        UniteMesurerService uniteMesurerService) {
                this.capteurService = capteurService;
                this.marqueService = marqueService;
                this.uniteMesurerService = uniteMesurerService;

                nom.setWidthFull();
                description.setWidthFull();
                reference.setWidthFull();
                hauteur.setWidthFull();
                longueur.setWidthFull();
                largeur.setWidthFull();
                marque.setWidthFull();

                description.setHeight("150px");

                marque.setItems(marqueService.getAllMarques());
                marque.setItemLabelGenerator(Marque::getNom);

                binder.forField(nom).asRequired("Le nom est requis").bind(Capteur::getNom, Capteur::setNom);
                binder.forField(description).bind(Capteur::getDescription, Capteur::setDescription);
                binder.forField(reference).bind(Capteur::getReference, Capteur::setReference);
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
                binder.forField(marque).asRequired("La marque est requise").bind(Capteur::getMarque,
                                Capteur::setMarque);

                addParametreButton.addClickListener(e -> addParametreCapteurForm(null));

                save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                save.addClickListener(e -> save());
                addKeyPressListener(Key.ENTER, e -> save());

                add(new H3("Informations générales"),
                                new HorizontalLayout(nom, reference),
                                description,
                                new HorizontalLayout(hauteur, longueur, largeur),
                                marque,
                                new H3("Paramètres de mesure"),
                                parametreListLayout,
                                addParametreButton,
                                save);

                setVisible(false);
        }

        private void addParametreCapteurForm(ParametreCapteur parametre) {
                Binder<ParametreCapteur> parametreBinder = new Binder<>(ParametreCapteur.class);

                ComboBox<UniteMesurer> unite = new ComboBox<>();
                unite.setItems(uniteMesurerService.getAllUniteMesurers());
                unite.setItemLabelGenerator(u -> u.getNom() + " (" + u.getSymbole() + ")");

                TextField min = new TextField("Min");
                TextField max = new TextField("Max");
                TextField prec = new TextField("Précision");

                parametreBinder.forField(unite).asRequired("Requis")
                                .bind(ParametreCapteur::getUniteMesurer, ParametreCapteur::setUniteMesurer);
                parametreBinder.forField(min).withNullRepresentation("")
                                .withConverter(new StringToIntegerConverter("Entier requis"))
                                .bind(ParametreCapteur::getPlageMin, ParametreCapteur::setPlageMin);
                parametreBinder.forField(max).withNullRepresentation("")
                                .withConverter(new StringToIntegerConverter("Entier requis"))
                                .bind(ParametreCapteur::getPlageMax, ParametreCapteur::setPlageMax);
                parametreBinder.forField(prec)
                                .withNullRepresentation("")
                                .withConverter(new StringToIntegerConverter("Entier requis"))
                                .bind(ParametreCapteur::getPrecision, ParametreCapteur::setPrecision);

                HorizontalLayout row = new HorizontalLayout(unite, min, max, prec);
                parametreListLayout.add(row);
                parametreBinders.add(parametreBinder);

                if (parametre != null) {
                        parametreBinder.readBean(parametre);
                }
        }

        private void save() {
                try {
                        binder.writeBean(capteur);
                        List<ParametreCapteur> list = new ArrayList<>();
                        for (Binder<ParametreCapteur> b : parametreBinders) {
                                ParametreCapteur p = new ParametreCapteur();
                                b.writeBean(p);
                                p.setCapteurId(capteur.getId());
                                if (p.getUniteMesurer() != null) {
                                        p.setUniteMesurerId(p.getUniteMesurer().getId());
                                } else {
                                        Notification.show("Une unité de mesure est manquante dans un paramètre", 3000,
                                                        Notification.Position.TOP_END);
                                        return;
                                }
                                list.add(p);
                        }
                        capteur.setParametreCapteur(list);
                        capteurService.saveCapteur(capteur);
                        if (changeHandler != null)
                                changeHandler.onChange();
                        setVisible(false);
                } catch (ValidationException e) {
                        Notification.show("Erreur de validation", 3000, Notification.Position.TOP_END);
                }
        }

        public void edit(Capteur c) {
                if (c == null) {
                        setVisible(false);
                        return;
                }
                this.capteur = c.getId() != null ? capteurService.getCapteurByIdWithParam(c.getId()) : c;
                binder.readBean(capteur);
                parametreListLayout.removeAll();
                parametreBinders.clear();
                List<ParametreCapteur> parametres = capteur.getParametreCapteur();
                if (parametres != null) {
                        for (ParametreCapteur p : parametres) {
                                if (p.getUniteMesurer() != null) {
                                        addParametreCapteurForm(p);
                                }
                        }
                }

                setVisible(true);
        }

        public void setChangeHandler(ChangeHandler h) {
                this.changeHandler = h;
        }

        public interface ChangeHandler {
                void onChange();
        }
}
