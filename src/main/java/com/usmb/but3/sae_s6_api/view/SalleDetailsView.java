package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.*;
import com.usmb.but3.sae_s6_api.service.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;


@Route("salle/:id")
@PageTitle("Détails Salle")
public class SalleDetailsView extends VerticalLayout implements BeforeEnterObserver {

    private final EquipementInstalleService equipementInstalleService;
    private final CapteurInstalleService capteurInstalleService;
    private final SalleService salleService;
    private final EquipementService equipementService;
    private final CapteurService capteurService;

    private final Grid<EquipementInstalle> equipementGrid = new Grid<>(EquipementInstalle.class, false);
    private final Grid<CapteurInstalle> capteurGrid = new Grid<>(CapteurInstalle.class, false);

    private Salle currentSalle;

public SalleDetailsView(
        EquipementInstalleService equipementInstalleService,
        CapteurInstalleService capteurInstalleService,
        SalleService salleService,
        EquipementService equipementService,
        CapteurService capteurService) {

    this.equipementInstalleService = equipementInstalleService;
    this.capteurInstalleService = capteurInstalleService;
    this.salleService = salleService;
    this.equipementService = equipementService;
    this.capteurService = capteurService;

    setWidthFull();
    setPadding(true);
    setSpacing(true);

    // GRID ÉQUIPEMENTS
    equipementGrid.addColumn(e -> e.getEquipement().getNom()).setHeader("Équipement");
    equipementGrid.addColumn(EquipementInstalle::getNombre).setHeader("Quantité");

    equipementGrid.addColumn(new ComponentRenderer<>(ei -> {
    Icon editIcon = VaadinIcon.EDIT.create();
    Icon deleteIcon = VaadinIcon.TRASH.create();

        // dialog edit
        Dialog editDialog = new Dialog();
    editDialog.setHeaderTitle("Modifier l'équipement");

    ComboBox<Equipement> combo = new ComboBox<>("Équipement");
    combo.setItems(equipementService.getAllEquipements());
    combo.setItemLabelGenerator(Equipement::getNom);
    combo.setValue(ei.getEquipement());

    NumberField quantite = new NumberField("Quantité");
    quantite.setMin(1);
    quantite.setValue((double) ei.getNombre());

    FormLayout form = new FormLayout(combo, quantite);
    editDialog.add(form);

    Button save = new Button("Enregistrer", e -> {
        if (combo.getValue() != null && quantite.getValue() != null) {
            ei.setEquipement(combo.getValue());
            ei.setNombre(quantite.getValue().intValue());
            equipementInstalleService.saveEquipementInstalle(ei);
            Notification.show("Équipement modifié");
            equipementGrid.setItems(equipementInstalleService.getBySalleId(currentSalle.getId()));
            editDialog.close();
        }
    });

    Button cancel = new Button("Annuler", e -> editDialog.close());
    editDialog.getFooter().add(cancel, save);

    // dialog delete
    Dialog deleteDialog = new Dialog();
    deleteDialog.setHeaderTitle("Supprimer l'équipement");
    deleteDialog.add("Voulez-vous vraiment supprimer " + ei.getEquipement().getNom() + " ?");

    Button confirm = new Button("Supprimer", e -> {
        equipementInstalleService.deleteEquipementInstalleById(ei.getId());
        equipementGrid.setItems(equipementInstalleService.getBySalleId(currentSalle.getId()));
        Notification.show("Équipement supprimé");
        deleteDialog.close();
    });
    confirm.addThemeVariants(ButtonVariant.LUMO_ERROR);
    Button cancelDelete = new Button("Annuler", e -> deleteDialog.close());
    deleteDialog.getFooter().add(cancelDelete, confirm);

    Button editBtn = new Button(editIcon, e -> editDialog.open());
    editBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    Button deleteBtn = new Button(deleteIcon, e -> deleteDialog.open());
    deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

    return new HorizontalLayout(editBtn, deleteBtn);
    })).setHeader("Actions");


    equipementGrid.setWidthFull();
    equipementGrid.setHeight("200px");

    // GRID CAPTEURS
    capteurGrid.addColumn(c -> c.getCapteur().getNom()).setHeader("Capteur");
    capteurGrid.addColumn(CapteurInstalle::getNombre).setHeader("Quantité");
    
    capteurGrid.addColumn(new ComponentRenderer<>(ci -> {
        Icon editIcon = VaadinIcon.EDIT.create();
        Icon deleteIcon = VaadinIcon.TRASH.create();
    
        // dialog edit
        Dialog editDialog = new Dialog();
        editDialog.setHeaderTitle("Modifier le capteur");
    
        ComboBox<Capteur> combo = new ComboBox<>("Capteur");
        combo.setItems(capteurService.getAllCapteurs());
        combo.setItemLabelGenerator(Capteur::getNom);
        combo.setValue(ci.getCapteur());
    
        NumberField quantite = new NumberField("Quantité");
        quantite.setMin(1);
        quantite.setValue((double) ci.getNombre());
    
        FormLayout form = new FormLayout(combo, quantite);
        editDialog.add(form);
    
        Button save = new Button("Enregistrer", e -> {
            if (combo.getValue() != null && quantite.getValue() != null) {
                ci.setCapteur(combo.getValue());
                ci.setNombre(quantite.getValue().intValue());
                capteurInstalleService.saveCapteurInstalle(ci);
                Notification.show("Capteur modifié");
                capteurGrid.setItems(capteurInstalleService.getBySalleId(currentSalle.getId()));
                editDialog.close();
            }
        });
    
        Button cancel = new Button("Annuler", e -> editDialog.close());
        editDialog.getFooter().add(cancel, save);
    
        // dialog delete
        Dialog deleteDialog = new Dialog();
        deleteDialog.setHeaderTitle("Supprimer le capteur");
        deleteDialog.add("Voulez-vous vraiment supprimer " + ci.getCapteur().getNom() + " ?");
    
        Button confirm = new Button("Supprimer", e -> {
            capteurInstalleService.deleteCapteurInstalleById(ci.getId());
            capteurGrid.setItems(capteurInstalleService.getBySalleId(currentSalle.getId()));
            Notification.show("Capteur supprimé");
            deleteDialog.close();
        });
        confirm.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button cancelDelete = new Button("Annuler", e -> deleteDialog.close());
        deleteDialog.getFooter().add(cancelDelete, confirm);
    
        Button editBtn = new Button(editIcon, e -> editDialog.open());
        editBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    
        Button deleteBtn = new Button(deleteIcon, e -> deleteDialog.open());
        deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
    
        return new HorizontalLayout(editBtn, deleteBtn);
    })).setHeader("Actions");
    
    

    capteurGrid.setWidthFull();
    capteurGrid.setHeight("200px");
}


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idParam = event.getRouteParameters().get("id").orElse(null);
        if (idParam == null) return;

        try {
            Integer salleId = Integer.parseInt(idParam);
            Salle salle = salleService.getSalleById(salleId);

            this.currentSalle = salle;
            removeAll();

            // Header
            HorizontalLayout salleCard = new HorizontalLayout();
            salleCard.setWidthFull();
            salleCard.setAlignItems(Alignment.CENTER);
            salleCard.getStyle()
                    .set("background-color", "#ffffff")
                    .set("border-radius", "12px")
                    .set("box-shadow", "0 4px 10px rgba(0,0,0,0.05)")
                    .set("padding", "20px")
                    .set("margin-bottom", "30px");
            
            // Gestion image
            String imageUrl = (salle.getUrlImg() != null && !salle.getUrlImg().isEmpty())
                    ? salle.getUrlImg()
                    : "images/imagesalle.jpg";
            
            Image salleImage = new Image(imageUrl, "Image de la salle");
            salleImage.setWidth("280px");
            salleImage.setHeight("auto");
            salleImage.getStyle()
                    .set("border-radius", "10px")
                    .set("object-fit", "cover")
                    .set("box-shadow", "0 2px 8px rgba(0,0,0,0.05)");
            
            // details de la salle
            VerticalLayout salleDetails = new VerticalLayout();
            salleDetails.setSpacing(false);
            salleDetails.setPadding(false);
            salleDetails.setWidthFull();
            salleDetails.add(
                new H3("Salle : "+ salle.getNom()),
                new Paragraph("Bâtiment : " + salle.getBatiment().getNom()),
                new Paragraph("Capacité : " + salle.getCapacite()),
                new Paragraph("Type : " + salle.getTypeSalle().getNom())
            );
            
            salleCard.add(salleImage, salleDetails);
            add(salleCard);
            

            // Dialogs
            Dialog equipementDialog = createEquipementDialog();
            Dialog capteurDialog = createCapteurDialog();

            // Boutons
            Button addEquipementButton = new Button("Ajouter un équipement", new Icon(VaadinIcon.PLUS));
            addEquipementButton.getStyle()
                    .set("background-color", "#2563eb")
                    .set("color", "white")
                    .set("border-radius", "8px")
                    .set("box-shadow", "0 2px 6px rgba(0,0,0,0.1)");

            Button addCapteurButton = new Button("Ajouter un capteur", new Icon(VaadinIcon.PLUS));
            addCapteurButton.getStyle()
                    .set("background-color", "#16a34a")
                    .set("color", "white")
                    .set("border-radius", "8px")
                    .set("box-shadow", "0 2px 6px rgba(0,0,0,0.1)");

            // Sections avec boutons alignés à droite
            HorizontalLayout equipementHeader = new HorizontalLayout(new H4("Équipements installés"), addEquipementButton);
            equipementHeader.setWidthFull();
            equipementHeader.setAlignItems(Alignment.CENTER);
            equipementHeader.setJustifyContentMode(JustifyContentMode.BETWEEN);

            HorizontalLayout capteurHeader = new HorizontalLayout(new H4("Capteurs installés"), addCapteurButton);
            capteurHeader.setWidthFull();
            capteurHeader.setAlignItems(Alignment.CENTER);
            capteurHeader.setJustifyContentMode(JustifyContentMode.BETWEEN);

            // Actions
            addEquipementButton.addClickListener(e -> equipementDialog.open());
            addCapteurButton.addClickListener(e -> capteurDialog.open());

            // Affichage final
            add(equipementHeader, equipementGrid);
            add(capteurHeader, capteurGrid);
            add(equipementDialog, capteurDialog);

            // Données
            equipementGrid.setItems(equipementInstalleService.getBySalleId(salleId));
            capteurGrid.setItems(capteurInstalleService.getBySalleId(salleId));

        } catch (NumberFormatException e) {
            removeAll();
            add(new H3("ID de salle invalide"));
        }
    }

    private Dialog createEquipementDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Ajouter un équipement");

        ComboBox<Equipement> equipementComboBox = new ComboBox<>("Équipement");
        equipementComboBox.setItems(equipementService.getAllEquipements());
        equipementComboBox.setItemLabelGenerator(Equipement::getNom);
        equipementComboBox.setWidthFull();

        NumberField nombreField = new NumberField("Quantité");
        nombreField.setMin(1);
        nombreField.setWidthFull();

        Button save = new Button("Enregistrer", e -> {
            Equipement equipement = equipementComboBox.getValue();
            if (equipement != null && nombreField.getValue() != null && nombreField.getValue() > 0) {
                EquipementInstalle ei = new EquipementInstalle();
                ei.setEquipement(equipement);
                ei.setNombre(nombreField.getValue().intValue());
                ei.setSalle(currentSalle);
                equipementInstalleService.saveEquipementInstalle(ei);
                equipementGrid.setItems(equipementInstalleService.getBySalleId(currentSalle.getId()));
                dialog.close();
            }
        });

        Button cancel = new Button("Annuler", e -> dialog.close());

        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout content = new VerticalLayout(equipementComboBox, nombreField, actions);
        content.setSpacing(true);
        content.setPadding(false);
        dialog.add(content);

        return dialog;
    }

    private Dialog createCapteurDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Ajouter un capteur");

        ComboBox<Capteur> capteurComboBox = new ComboBox<>("Capteur");
        capteurComboBox.setItems(capteurService.getAllCapteurs());
        capteurComboBox.setItemLabelGenerator(Capteur::getNom);
        capteurComboBox.setWidthFull();

        NumberField nombreField = new NumberField("Quantité");
        nombreField.setMin(1);
        nombreField.setWidthFull();

        Button save = new Button("Enregistrer", e -> {
            Capteur capteur = capteurComboBox.getValue();
            if (capteur != null && nombreField.getValue() != null && nombreField.getValue() > 0) {
                CapteurInstalle ci = new CapteurInstalle();
                ci.setCapteur(capteur);
                ci.setNombre(nombreField.getValue().intValue());
                ci.setSalle(currentSalle);
                capteurInstalleService.saveCapteurInstalle(ci);
                capteurGrid.setItems(capteurInstalleService.getBySalleId(currentSalle.getId()));
                dialog.close();
            }
        });

        Button cancel = new Button("Annuler", e -> dialog.close());

        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout content = new VerticalLayout(capteurComboBox, nombreField, actions);
        content.setSpacing(true);
        content.setPadding(false);
        dialog.add(content);

        return dialog;
    }
}
