package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.CapteurInstalle;
import com.usmb.but3.sae_s6_api.entity.EquipementInstalle;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.service.CapteurInstalleService;
import com.usmb.but3.sae_s6_api.service.EquipementInstalleService;
import com.usmb.but3.sae_s6_api.service.SalleService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

import java.util.List;

@Route("salle/:id")
@PageTitle("Détails Salle")
public class SalleDetailsView extends VerticalLayout implements BeforeEnterObserver {

    private final EquipementInstalleService equipementInstalleService;
    private final CapteurInstalleService capteurInstalleService;
    private final SalleService salleService;

    private final Grid<EquipementInstalle> equipementGrid = new Grid<>(EquipementInstalle.class, false);
    private final Grid<CapteurInstalle> capteurGrid = new Grid<>(CapteurInstalle.class, false);

    public SalleDetailsView(EquipementInstalleService equipementInstalleService,CapteurInstalleService capteurInstalleService,SalleService salleService) {
        this.equipementInstalleService = equipementInstalleService;
        this.capteurInstalleService = capteurInstalleService;
        this.salleService = salleService;

        setWidthFull();
        setPadding(true);
        setSpacing(true);

        equipementGrid.addColumn(e -> e.getEquipement().getNom()).setHeader("Équipement");
        equipementGrid.addColumn(EquipementInstalle::getNombre).setHeader("Quantité");
        equipementGrid.setWidthFull();
        equipementGrid.setHeight("200px");

        capteurGrid.addColumn(e -> e.getCapteur().getNom()).setHeader("Capteur");
        capteurGrid.addColumn(CapteurInstalle::getNombre).setHeader("Quantité");
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
            if (salle == null) {
                removeAll();
                add(new H3("Salle non trouvée"));
                return;
            }

            removeAll();

            // Header avec bouton retour
            HorizontalLayout header = new HorizontalLayout();
            header.setWidthFull();
            header.setJustifyContentMode(JustifyContentMode.BETWEEN);
            header.setAlignItems(Alignment.CENTER);

            H3 title = new H3("Détails de la salle : " + salle.getNom());

            header.add(title);
            add(header);

            // Infos salle
            Div salleInfo = new Div();
            salleInfo.getStyle().set("padding", "10px");
            salleInfo.getStyle().set("background-color", "#f9f9f9");
            salleInfo.getStyle().set("border-radius", "8px");
            salleInfo.getStyle().set("box-shadow", "0 1px 4px rgba(0,0,0,0.1)");
            salleInfo.add(
                new Paragraph("Capacité : " + salle.getCapacite()),
                new Paragraph("Type : " + salle.getTypeSalle().getNom()),
                new Paragraph("Bâtiment : " + salle.getBatiment().getNom())
            );
            add(salleInfo);

            add(new H4("Équipements installés"));
            add(equipementGrid);

            add(new H4("Capteurs installés"));
            add(capteurGrid);

            
            List<EquipementInstalle> equipements = equipementInstalleService.getBySalleId(salleId);
            List<CapteurInstalle> capteurs = capteurInstalleService.getBySalleId(salleId);

            equipementGrid.setItems(equipements);
            capteurGrid.setItems(capteurs);

        } catch (NumberFormatException e) {

            removeAll();
            add(new H3("ID de salle invalide"));
        }
    }
}
