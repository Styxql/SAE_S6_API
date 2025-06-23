package com.usmb.but3.sae_s6_api.view;

import java.math.BigDecimal;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.service.EquipementService;
import com.usmb.but3.sae_s6_api.service.EquipementInstalleService;
import com.usmb.but3.sae_s6_api.service.MarqueService;
import com.usmb.but3.sae_s6_api.service.TypeEquipementService;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * Composant Vaadin pour l'édition d'équipements.
 * Permet de créer ou modifier un équipement avec ses dimensions, marque et type.
 */
@SpringComponent
@UIScope
public class EquipementEditor extends VerticalLayout implements KeyNotifier {

    // Services nécessaires
    private final EquipementService equipementService;

    // Objet Equipement en cours d'édition
    private Equipement equipement;

    // Champs du formulaire
    private final TextField nom = new TextField("Nom");
    private final BigDecimalField hauteur = new BigDecimalField("Hauteur (cm)");
    private final BigDecimalField longueur = new BigDecimalField("Longueur (cm)");
    private final BigDecimalField largeur = new BigDecimalField("Largeur (cm)");
    private final ComboBox<Marque> marque = new ComboBox<>("Marque");
    private final ComboBox<TypeEquipement> typeEquipement = new ComboBox<>("Type d'équipement");

    // Bouton d'action
    private final Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());

    // Binder principal
    private final Binder<Equipement> binder = new Binder<>(Equipement.class);

    // Callback pour signaler un changement externe
    private ChangeHandler changeHandler;

    public EquipementEditor(
            EquipementService equipementService,
            EquipementInstalleService equipementInstalleService,
            MarqueService marqueService,
            TypeEquipementService typeEquipementService) {

        this.equipementService = equipementService;

        configureComboBoxes(marqueService, typeEquipementService);
        configureBindings();
        buildForm();

        setSpacing(true);
        setVisible(false);
    }

    /**
     * Configure les ComboBox (marque et type d'équipement).
     */
    private void configureComboBoxes(MarqueService marqueService, TypeEquipementService typeEquipementService) {
        marque.setItems(marqueService.getAllMarques());
        marque.setItemLabelGenerator(Marque::getNom);
        marque.setWidthFull();

        typeEquipement.setItems(typeEquipementService.getAllTypeEquipements());
        typeEquipement.setItemLabelGenerator(TypeEquipement::getNom);
        typeEquipement.setWidthFull();
    }

    /**
     * Configure les bindings des champs avec validation.
     */
    private void configureBindings() {
        binder.forField(nom)
                .asRequired("Le nom est requis")
                .bind(Equipement::getNom, Equipement::setNom);

        binder.forField(hauteur)
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "Hauteur invalide")
                .bind(Equipement::getHauteur, Equipement::setHauteur);

        binder.forField(longueur)
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "Longueur invalide")
                .bind(Equipement::getLongueur, Equipement::setLongueur);

        binder.forField(largeur)
                .withValidator(d -> d != null && d.compareTo(BigDecimal.ZERO) > 0, "Largeur invalide")
                .bind(Equipement::getLargeur, Equipement::setLargeur);

        binder.forField(marque)
                .asRequired("La marque est requise")
                .bind(Equipement::getMarque, Equipement::setMarque);

        binder.forField(typeEquipement)
                .asRequired("Le type d'équipement est requis")
                .bind(Equipement::getTypeEquipement, Equipement::setTypeEquipement);
    }

    /**
     * Construit le formulaire à afficher.
     */
    private void buildForm() {
        HorizontalLayout dimensionsLayout = new HorizontalLayout(nom, hauteur, longueur, largeur);
        dimensionsLayout.setWidthFull();
        dimensionsLayout.setFlexGrow(1, nom, hauteur, longueur, largeur);

        HorizontalLayout selectionLayout = new HorizontalLayout(marque, typeEquipement);
        selectionLayout.setWidthFull();
        selectionLayout.setFlexGrow(1, marque, typeEquipement);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> save());
        addKeyPressListener(Key.ENTER, e -> save());

        add(
            new H3("Informations sur l'équipement"),
            dimensionsLayout,
            selectionLayout,
            save
        );
    }

    /**
     * Sauvegarde l'équipement après validation des champs.
     */
    private void save() {
        try {
            binder.writeBean(equipement);
            equipementService.saveEquipement(equipement);
            if (changeHandler != null) changeHandler.onChange();
            setVisible(false);
        } catch (ValidationException e) {
            Notification.show("Erreur : certains champs sont invalides.", 3000, Notification.Position.TOP_END);
        }
    }

    /**
     * Prépare l'édition d'un équipement donné.
     */
    public void edit(Equipement equipement) {
        if (equipement == null) {
            setVisible(false);
            return;
        }

        this.equipement = (equipement.getId() != null)
                ? equipementService.getEquipementById(equipement.getId())
                : equipement;

        binder.readBean(this.equipement);
        setVisible(true);
        nom.focus();
    }

    /**
     * Définit le handler à appeler lorsqu'une modification est enregistrée.
     */
    public void setChangeHandler(ChangeHandler h) {
        this.changeHandler = h;
    }

    /**
     * Interface permettant de notifier un changement à l'extérieur du composant.
     */
    public interface ChangeHandler {
        void onChange();
    }
}
