package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.service.BatimentService;
import com.usmb.but3.sae_s6_api.service.SalleService;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.util.List;

@Route("batiment/:id")
public class SalleView extends VerticalLayout implements BeforeEnterObserver {

    private final BatimentService batimentService;
    private final SalleService salleService;

    private Dialog dialogAjoutSalle;
    private TextField nomField;
    private TextField imageField;
    private TextField capaciteField;
    private Integer batimentIdCourant;
    private Div grid;

    public SalleView(BatimentService batimentService, SalleService salleService) {
        this.batimentService = batimentService;
        this.salleService = salleService;

        setPadding(true);
        setSpacing(true);
        setSizeFull();

        getStyle().set("padding", "50px");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idParam = event.getRouteParameters().get("id").orElse(null);
        Integer id = Integer.parseInt(idParam);
        batimentIdCourant = id;

        Batiment bat = batimentService.getBatimentById(id);

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Salles du bâtiment : " + bat.getNom());
        Button addButton = new Button("Ajouter une salle", VaadinIcon.PLUS.create());
        addButton.getStyle()
            .set("background-color", "rgba(89, 147, 255, 0.15)")
            .set("border-radius", "8px")
            .set("padding", "8px 16px")
            .set("font-weight", "500")
            .set("color", "#2c3e50")
            .set("border", "none");

        addButton.addClickListener(e -> {
            nomField.clear();
            imageField.clear();
            capaciteField.clear();
            dialogAjoutSalle.open();
        });

        header.add(title, addButton);
        add(header);

        setupAjoutSalleDialog();
        afficherSalles(batimentIdCourant);
    }

    private void setupAjoutSalleDialog() {
        dialogAjoutSalle = new Dialog();
        dialogAjoutSalle.setHeaderTitle("Ajouter une nouvelle salle");

        nomField = new TextField("Nom");
        imageField = new TextField("URL de l’image");
        capaciteField = new TextField("Capacité");

        FormLayout formLayout = new FormLayout(nomField, imageField, capaciteField);
        dialogAjoutSalle.add(formLayout);

        Button saveButton = new Button("Enregistrer", event -> {
            String nom = nomField.getValue().trim();
            String url = imageField.getValue().trim();
            String capaciteStr = capaciteField.getValue().trim();

            if (nom.isEmpty()) {
                Notification.show("Le nom est obligatoire", 3000, Notification.Position.MIDDLE);
                return;
            }

            Salle newSalle = new Salle();
            newSalle.setNom(nom);
            newSalle.setUrlImg(url.isEmpty() ? null : url);
            newSalle.setId(batimentIdCourant);
            try {  
                newSalle.setCapacite(Integer.parseInt(capaciteStr));
            } catch (NumberFormatException ex) {
                newSalle.setCapacite(null);
            }

            salleService.saveSalle(newSalle);
            Notification.show("Salle ajoutée avec succès");
            dialogAjoutSalle.close();
            refreshSalles();
        });

        Button cancelButton = new Button("Annuler", e -> dialogAjoutSalle.close());
        dialogAjoutSalle.getFooter().add(cancelButton, saveButton);
        add(dialogAjoutSalle);
    }

    private void afficherSalles(Integer batimentId) {
        if (grid != null) {
            remove(grid);
        }

        grid = new Div();
        grid.getStyle()
            .set("display", "grid")
            .set("grid-template-columns", "repeat(3, 1fr)")
            .set("gap", "20px")
            .set("padding", "10px");

        List<Salle> salles = salleService.getByBatimentId(batimentId);
        if (salles.isEmpty()) {
            grid.add(new Span("Aucune salle dans ce bâtiment."));
        } else {
            for (Salle salle : salles) {
                grid.add(createSalleCard(salle));
            }
        }

        add(grid);
    }

    private void refreshSalles() {
        afficherSalles(batimentIdCourant);
    }

    private Card createSalleCard(Salle salle) {
        Card card = new Card();
        card.setWidth("350px");
        card.setHeight("250px");
        card.getStyle()
            .set("border-radius", "10px")
            .set("box-shadow", "0 2px 6px rgba(0, 0, 0, 0.15)")
            .set("overflow", "hidden")
            .set("padding", "0")
            .set("background-color", "white");

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);
        content.setMargin(false);
        content.setWidthFull();
        content.setAlignItems(Alignment.START);

        String imageUrl = (salle.getUrlImg() != null && !salle.getUrlImg().isEmpty())
            ? salle.getUrlImg()
            : "images/imagesalle.jpg";

        Image image = new Image(imageUrl, "Image de " + salle.getNom());
        image.setWidthFull();
        image.setHeight("200px");
        image.getStyle().set("object-fit", "cover");

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(HorizontalLayout.JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.getStyle().set("padding", "8px 12px");

        Icon roomIcon = VaadinIcon.ARCHIVES.create();
        roomIcon.setSize("20px");

        Span nom = new Span(salle.getNom());
        nom.getStyle().set("font-weight", "bold").set("font-size", "1.1em");

        HorizontalLayout nameWithIcon = new HorizontalLayout(roomIcon, nom);
        nameWithIcon.setSpacing(true);
        nameWithIcon.setAlignItems(Alignment.CENTER);

        MenuBar menuBar = new MenuBar();
        menuBar.getElement()
            .addEventListener("click", e -> {})
            .addEventData("event.stopPropagation()");
        MenuItem menuIcon = menuBar.addItem(new Icon(VaadinIcon.ELLIPSIS_DOTS_V));
        SubMenu subMenu = menuIcon.getSubMenu();

        // Modifier
        subMenu.addItem("Modifier", e -> {
            Dialog editDialog = new Dialog();
            editDialog.setHeaderTitle("Modifier la salle");

            TextField nomField = new TextField("Nom");
            nomField.setValue(salle.getNom());

            TextField imageField = new TextField("URL de l’image");
            imageField.setValue(salle.getUrlImg() != null ? salle.getUrlImg() : "");

            TextField capaciteField = new TextField("Capacité");
            capaciteField.setValue(salle.getCapacite() != null ? salle.getCapacite().toString() : "");

            FormLayout formLayout = new FormLayout(nomField, imageField, capaciteField);
            editDialog.add(formLayout);

            Button saveButton = new Button("Enregistrer", event -> {
                salle.setNom(nomField.getValue().trim());
                salle.setUrlImg(imageField.getValue().trim());
                try {
                    salle.setCapacite(Integer.parseInt(capaciteField.getValue()));
                } catch (NumberFormatException ex) {
                    salle.setCapacite(null);
                }

                salleService.saveSalle(salle);
                Notification.show("Salle modifiée");
                editDialog.close();
                getUI().ifPresent(ui -> ui.getPage().reload());
            });

            Button cancelButton = new Button("Annuler", e2 -> editDialog.close());
            editDialog.getFooter().add(cancelButton, saveButton);

            add(editDialog);
            editDialog.open();
        });

        // Supprimer
        subMenu.addItem("Supprimer", e -> {
            Dialog confirmDialog = new Dialog();
            confirmDialog.setHeaderTitle("Confirmer la suppression");

            confirmDialog.add("Voulez-vous vraiment supprimer la salle \"" + salle.getNom() + "\" ?");

            Button confirmButton = new Button("Supprimer", event -> {
                salleService.deleteSalleById(salle.getId());
                Notification.show("Salle supprimée");
                confirmDialog.close();
                getUI().ifPresent(ui -> ui.getPage().reload());
            });
            confirmButton.getStyle().set("color", "red");

            Button cancelButton = new Button("Annuler", event -> confirmDialog.close());
            confirmDialog.getFooter().add(cancelButton, confirmButton);

            add(confirmDialog);
            confirmDialog.open();
        });

        headerLayout.add(nameWithIcon, menuBar);
        content.add(image, headerLayout);
        card.add(content);

        return card;
    }
}
