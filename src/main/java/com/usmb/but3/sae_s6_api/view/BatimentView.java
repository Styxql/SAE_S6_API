package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.service.BatimentService;
import com.usmb.but3.sae_s6_api.service.SalleService;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;


import java.util.List;

@Route("batiment")
public class BatimentView extends VerticalLayout {
    private final BatimentService batimentService;
    private final SalleService salleService;

public BatimentView(BatimentService batimentService, SalleService salleService) {
    this.batimentService = batimentService;
    this.salleService = salleService;

    setPadding(false);
    setSpacing(true);
    setSizeFull();

    getStyle()
        .set("padding", "50px");

    HorizontalLayout header = new HorizontalLayout();
    header.setWidthFull();
    header.setJustifyContentMode(JustifyContentMode.BETWEEN);
    header.setAlignItems(Alignment.CENTER);

    H3 title = new H3("Liste des bâtiments");

    Button addButton = new Button("Ajouter un bâtiment", VaadinIcon.PLUS.create());
    addButton.getStyle()
        .set("background-color", "rgba(89, 147, 255, 0.15)")
        .set("border-radius", "8px")
        .set("padding", "8px 16px")
        .set("font-weight", "500")
        .set("color", "#2c3e50")
        .set("border", "none");

    header.add(title, addButton);
    add(header);
    Div cardLayout = new Div();
    cardLayout.getStyle()
        .set("display", "grid")
        .set("grid-template-columns", "repeat(3, 1fr)")
        .set("gap", "20px")
        .set("padding", "10px");

    add(cardLayout);

    refreshBatimentCards(cardLayout);


    Dialog dialog = new Dialog();
    dialog.setHeaderTitle("Nouveau bâtiment");

    TextField nomField = new TextField("Nom");
    TextField imageField = new TextField("URL de l’image");

    FormLayout formLayout = new FormLayout();
    formLayout.add(nomField, imageField);
    dialog.add(formLayout);

    Button saveButton = new Button("Enregistrer", event -> {
        String nom = nomField.getValue().trim();
        String url = imageField.getValue().trim();

        if (nom.isEmpty()) {
            Notification.show("Le nom est obligatoire", 3000, Notification.Position.MIDDLE);
            return;
        }

        Batiment newBat = new Batiment();
        newBat.setNom(nom);
        newBat.setUrlImg(url.isEmpty() ? null : url);

        batimentService.saveBatiment(newBat);
        Notification.show("Bâtiment ajouté avec succès");
        dialog.close();

        refreshBatimentCards(cardLayout);
    });

    Button cancelButton = new Button("Annuler", e -> dialog.close());
    dialog.getFooter().add(cancelButton, saveButton);

    addButton.addClickListener(e -> {
        nomField.clear();
        imageField.clear();
        dialog.open();
    });

    add(dialog);
}

private Card createBatimentCard(Batiment bat) {
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

    String imageUrl = (bat.getUrlImg() != null && !bat.getUrlImg().isEmpty())
        ? bat.getUrlImg()
        : "images/image.jpg";

    Image image = new Image(imageUrl, "Image de " + bat.getNom());
    image.setWidthFull();
    image.setHeight("200px");
    image.getStyle().set("object-fit", "cover");

    // --- Header avec nom, salle count et menu
    HorizontalLayout headerLayout = new HorizontalLayout();
    headerLayout.setWidthFull();
    headerLayout.setJustifyContentMode(HorizontalLayout.JustifyContentMode.BETWEEN);
    headerLayout.setAlignItems(Alignment.CENTER);
    headerLayout.getStyle().set("padding", "8px 12px");

    Icon buildingIcon = VaadinIcon.BUILDING.create();
    buildingIcon.setSize("20px");

    Span nom = new Span(bat.getNom());
    nom.getStyle()
        .set("font-weight", "bold")
        .set("font-size", "1.1em");

    HorizontalLayout nameWithIcon = new HorizontalLayout(buildingIcon, nom);
    nameWithIcon.setSpacing(true);
    nameWithIcon.setAlignItems(Alignment.CENTER);

    int nbSalles = salleService.getByBatimentId(bat.getId()).size();
    Span salleCount = new Span(nbSalles + " salle" + (nbSalles > 1 ? "s" : ""));
    salleCount.getStyle()
        .set("background-color", "rgba(89, 147, 255, 0.15)")
        .set("border-radius", "12px")
        .set("padding", "4px 10px")
        .set("font-size", "0.85em");

    // Menu 3 points
    MenuBar menuBar = new MenuBar();
    menuBar.getElement()
        .addEventListener("click", e -> {})
        .addEventData("event.stopPropagation()");
    MenuItem menuIcon = menuBar.addItem(new Icon(VaadinIcon.ELLIPSIS_DOTS_V));

    SubMenu subMenu = menuIcon.getSubMenu();

    // --- Modifier
    subMenu.addItem("Modifier", e -> {
        Dialog editDialog = new Dialog();
        editDialog.setHeaderTitle("Modifier le bâtiment");

        TextField nomField = new TextField("Nom");
        nomField.setValue(bat.getNom());

        TextField imageField = new TextField("URL de l’image");
        imageField.setValue(bat.getUrlImg() != null ? bat.getUrlImg() : "");

        FormLayout formLayout = new FormLayout();
        formLayout.add(nomField, imageField);
        editDialog.add(formLayout);

        Button saveButton = new Button("Enregistrer", event -> {
            String nomModif = nomField.getValue().trim();
            String url = imageField.getValue().trim();

            if (nomModif.isEmpty()) {
                Notification.show("Le nom est obligatoire", 3000, Notification.Position.MIDDLE);
                return;
            }

            bat.setNom(nomModif);
            bat.setUrlImg(url.isEmpty() ? null : url);
            batimentService.saveBatiment(bat);
            Notification.show("Bâtiment modifié avec succès");
            editDialog.close();
            refreshBatimentCards((Div) card.getParent().get());
        });

        Button cancelButton = new Button("Annuler", e2 -> editDialog.close());
        editDialog.getFooter().add(cancelButton, saveButton);

        add(editDialog);
        editDialog.open();
    });

    // --- Supprimer
    subMenu.addItem("Supprimer", e -> {
        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle("Confirmer la suppression");

        confirmDialog.add("Voulez-vous vraiment supprimer le bâtiment \"" + bat.getNom() + "\" ?");

        Button confirmButton = new Button("Supprimer", event -> {
            batimentService.deleteBatimentById(bat.getId());
            Notification.show("Bâtiment supprimé");
            confirmDialog.close();
            refreshBatimentCards((Div) card.getParent().get());
        });
        confirmButton.getStyle().set("color", "red");

        Button cancelButton = new Button("Annuler", event -> confirmDialog.close());

        confirmDialog.getFooter().add(cancelButton, confirmButton);
        add(confirmDialog);
        confirmDialog.open();
    });

    headerLayout.add(nameWithIcon, salleCount, menuBar);

    content.add(image, headerLayout);
    card.add(content);

    // Navigation
    card.getElement().addEventListener("click", e -> {
        getUI().ifPresent(ui -> ui.navigate("batiment/" + bat.getId()));
    });
    card.getStyle().set("cursor", "pointer");

    return card;
}


    private void refreshBatimentCards(Div cardLayout) {
        cardLayout.removeAll();
        List<Batiment> batiments = batimentService.getAllBatiments();
        for (Batiment bat : batiments) {
            cardLayout.add(createBatimentCard(bat));
        }
    }
    
}

