package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.service.EquipementInstalleService;
import com.usmb.but3.sae_s6_api.service.EquipementService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class EquipementEditor extends VerticalLayout implements KeyNotifier {

    private final EquipementService equipementService;
    private final EquipementInstalleService equipementInstalleService;

    private Equipement equipement;

    TextField nom = new TextField("Nom");
    TextField hauteur = new TextField("Hauteur");
    TextField longueur = new TextField("Longueur");
    TextField largeur = new TextField("Largeur");

    HorizontalLayout fields = new HorizontalLayout(nom, hauteur, longueur, largeur);

    Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    Button cancel = new Button("Annuler");
    Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Equipement> binder = new Binder<>(Equipement.class);
    private ChangeHandler changeHandler;

    public EquipementEditor(EquipementService equipementService, EquipementInstalleService equipementInstalleService) {
        this.equipementService = equipementService;
        this.equipementInstalleService = equipementInstalleService;

        add(fields, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(equipement));

        setVisible(false);
    }

    void delete() {
        equipementService.deleteEquipementById(equipement.getId());
        changeHandler.onChange();
    }

    void save() {
        equipementService.saveEquipement(equipement);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void edit(Equipement a) {
        if (a == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = a.getId() != null;
        if (persisted) {
            equipement = equipementService.getEquipementById(a.getId());
        } else {
            equipement = a;
        }

        cancel.setVisible(persisted);
        binder.setBean(equipement);
        setVisible(true);
        nom.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        this.changeHandler = h;
    }
}
