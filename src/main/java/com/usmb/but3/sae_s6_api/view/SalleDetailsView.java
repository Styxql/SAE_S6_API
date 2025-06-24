package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.*;
import com.usmb.but3.sae_s6_api.service.*;
import com.usmb.but3.sae_s6_api.view.editor.CapteurInstalleEditor;
import com.usmb.but3.sae_s6_api.view.editor.EquipementInstalleEditor;
import com.usmb.but3.sae_s6_api.view.notification.NotificationType;
import com.usmb.but3.sae_s6_api.view.notification.Notifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;

@Route("salle")
@PageTitle("Détails Salle")
public class SalleDetailsView extends VerticalLayout implements HasUrlParameter<Integer> {

    private final EquipementInstalleService equipementInstalleService;
    private final CapteurInstalleService capteurInstalleService;
    private final SalleService salleService;
    private final EquipementService equipementService;
    private final CapteurService capteurService;

    private Salle salle;

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
    }
        
    private final Grid<EquipementInstalle> equipementInstalleGrid = new Grid<>(EquipementInstalle.class, false);
    private final Grid<CapteurInstalle> capteurInstalleGrid = new Grid<>(CapteurInstalle.class, false);

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {    
        if (parameter == null) {
            event.forwardTo("batiment");
            return;
        }

        this.salle = this.salleService.getSalleById(parameter);

        equipementInstalleGrid.addColumn(e -> e.getEquipement().getNom()).setHeader("Équipement");
        equipementInstalleGrid.addColumn(EquipementInstalle::getNombre).setHeader("Quantité");

        equipementInstalleGrid.addColumn(new ComponentRenderer<>(equipementInstalle -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();

            /*
             * Action -> event
             */
            Button editButton = new Button(editIcon, e -> {
                EquipementInstalleEditor editForm = new EquipementInstalleEditor(equipementInstalleModif -> {
                    equipementInstalleService.saveEquipementInstalle(equipementInstalleModif);
                    Notifier.show(equipementInstalleModif.getEquipement().getNom(), NotificationType.SUCCES_ADD);
                    refreshEquipementInstalle();
                },equipementService.getAllEquipements(), salle);
                editForm.editEquipementInstalle(equipementInstalle);
                add(editForm);
                editForm.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            // dialog delete
            Dialog deleteDialog = new Dialog();
            deleteDialog.setHeaderTitle("Supprimer l'Equipement Installe");
            deleteDialog.add("Voulez-vous vraiment supprimer " + equipementInstalle.getEquipement().getNom() + " ?");

            Button confirm = new Button("Supprimer", e -> {
                equipementInstalleService.deleteEquipementInstalleById(equipementInstalle.getId());
                Notifier.show(equipementInstalle.getEquipement().getNom(), NotificationType.SUCCES_REMOVE);
                deleteDialog.close();

                refreshEquipementInstalle();
            });
            
            confirm.addThemeVariants(ButtonVariant.LUMO_ERROR);
            Button cancelDelete = new Button("Annuler", e -> deleteDialog.close());
            deleteDialog.getFooter().add(cancelDelete, confirm);

            
            Button deleteButton = new Button(deleteIcon, e -> deleteDialog.open());
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");


        capteurInstalleGrid.addColumn(c -> c.getCapteur().getNom()).setHeader("Capteur");
        capteurInstalleGrid.addColumn(CapteurInstalle::getNombre).setHeader("Quantité");

        capteurInstalleGrid.addColumn(new ComponentRenderer<>(capteurInstalle -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();

            Button editButton = new Button(editIcon, e -> {
                CapteurInstalleEditor editForm = new CapteurInstalleEditor(capteurInstalleModif -> {
                    capteurInstalleService.saveCapteurInstalle(capteurInstalleModif);
                    Notifier.show(capteurInstalleModif.getCapteur().getNom(), NotificationType.SUCCES_EDIT);
                    refreshCapteurInstalle();
                }, capteurService.getAllCapteurs(), salle);
                editForm.editCapteurInstalle(capteurInstalle);
                add(editForm);
                editForm.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    

            Dialog deleteDialog = new Dialog();
                deleteDialog.setHeaderTitle("Supprimer le Capteur Installe");
                deleteDialog.add("Voulez-vous vraiment supprimer " + capteurInstalle.getCapteur().getNom() + " ?");

                Button confirm = new Button("Supprimer", e -> {
                    capteurInstalleService.deleteCapteurInstalleById(capteurInstalle.getId());
                    Notifier.show(capteurInstalle.getCapteur().getNom(), NotificationType.SUCCES_REMOVE);
                    deleteDialog.close();

                    refreshCapteurInstalle();
                });
                
                confirm.addThemeVariants(ButtonVariant.LUMO_ERROR);
                Button cancelDelete = new Button("Annuler", e -> deleteDialog.close());
                deleteDialog.getFooter().add(cancelDelete, confirm);

                
                Button deleteButton = new Button(deleteIcon, e -> deleteDialog.open());
                deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");

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
                    new H3("Salle : " + salle.getNom()),
                    new Paragraph("Bâtiment : " + salle.getBatiment().getNom()),
                    new Paragraph("Capacité : " + salle.getCapacite()),
                    new Paragraph("Type : " + salle.getTypeSalle().getNom()));

            salleCard.add(salleImage, salleDetails);
            add(salleCard);

            // Boutons
            Button addEquipementButton = new Button("Ajouter un équipement", new Icon(VaadinIcon.PLUS));
            addEquipementButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            Button addCapteurButton = new Button("Ajouter un capteur", new Icon(VaadinIcon.PLUS));
            addCapteurButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            HorizontalLayout equipementHeader = new HorizontalLayout(new H4("Équipements installés"), addEquipementButton);
            equipementHeader.setWidthFull();
            equipementHeader.setAlignItems(Alignment.CENTER);
            equipementHeader.setJustifyContentMode(JustifyContentMode.BETWEEN);

            HorizontalLayout capteurHeader = new HorizontalLayout(new H4("Capteurs installés"), addCapteurButton);
            capteurHeader.setWidthFull();
            capteurHeader.setAlignItems(Alignment.CENTER);
            capteurHeader.setJustifyContentMode(JustifyContentMode.BETWEEN);


            add(equipementHeader, equipementInstalleGrid);
            add(capteurHeader, capteurInstalleGrid);


            EquipementInstalleEditor editorEquipementInstalle = new EquipementInstalleEditor(equipementInstalle -> {
                equipementInstalleService.saveEquipementInstalle(equipementInstalle);
                Notifier.show(equipementInstalle.getEquipement().getNom(), NotificationType.SUCCES_NEW);
                refreshEquipementInstalle();
            }, equipementService.getAllEquipements(), salle);
    
            addEquipementButton.addClickListener(e -> {
                editorEquipementInstalle.editEquipementInstalle(new EquipementInstalle());
                add(editorEquipementInstalle);
                editorEquipementInstalle.open();
            });

            CapteurInstalleEditor editorCapteurInstalle = new CapteurInstalleEditor(capteurInstalle -> {
                capteurInstalleService.saveCapteurInstalle(capteurInstalle);
                Notifier.show(capteurInstalle.getCapteur().getNom(), NotificationType.SUCCES_NEW);
                refreshCapteurInstalle();
            }, capteurService.getAllCapteurs(), salle);
    
            addCapteurButton.addClickListener(e -> {
                editorCapteurInstalle.editCapteurInstalle(new CapteurInstalle());
                add(editorCapteurInstalle);
                editorCapteurInstalle.open();
            });

            allRefreshInstalle();
    }


    private void refreshEquipementInstalle()
    {
        equipementInstalleGrid.setItems(equipementInstalleService.getBySalleId(salle.getId()));
    }

    private void refreshCapteurInstalle()
    {
        capteurInstalleGrid.setItems(capteurInstalleService.getBySalleId(salle.getId()));
    }

    private void allRefreshInstalle()
    {
        refreshCapteurInstalle();
        refreshEquipementInstalle();
    }
}
    
