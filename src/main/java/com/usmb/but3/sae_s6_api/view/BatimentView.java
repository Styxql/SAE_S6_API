package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.service.BatimentService;
import com.usmb.but3.sae_s6_api.service.SalleService;
import com.usmb.but3.sae_s6_api.view.notification.Notifier;
import com.usmb.but3.sae_s6_api.view.editor.BatimentEditor;
import com.usmb.but3.sae_s6_api.view.notification.NotificationType;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;

import java.util.List;

@Route("batiment")
@PageTitle("Batiment")
@Menu(title = "Batiment", order = 0, icon = "vaadin:building") 
public class BatimentView extends VerticalLayout {
    private final BatimentService batimentService;
    private final SalleService salleService;

public BatimentView(BatimentService batimentService, SalleService salleService) {
    this.batimentService = batimentService;
    this.salleService = salleService;

    HorizontalLayout header = new HorizontalLayout();
    header.setWidthFull(); // N'a pas par défault 100% width
    header.setJustifyContentMode(JustifyContentMode.BETWEEN);
    header.setAlignItems(Alignment.CENTER);

    H3 title = new H3("Liste des bâtiments");

    Button addButton = new Button("Ajouter un bâtiment", VaadinIcon.PLUS.create());

    header.add(title, addButton);
   
    Div cardLayout = new Div();
    cardLayout.getStyle()
        .set("display", "grid")
        .set("grid-template-columns", "repeat(3, 1fr)")
        .set("gap", "20px")
        .set("padding", "10px");


    BatimentEditor editor = new BatimentEditor(batiment -> {
        batimentService.saveBatiment(batiment);
        Notifier.show(batiment.getNom(), NotificationType.SUCCES_NEW);
        refreshBatimentCards(cardLayout);
    });

    addButton.addClickListener(e -> {
        editor.editBatiment(new Batiment());
        add(editor);
        editor.open();
    });
    
    add(header, cardLayout);
    refreshBatimentCards(cardLayout);
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
        BatimentEditor editForm = new BatimentEditor(batModif -> {
            batimentService.saveBatiment(batModif);
            Notifier.show(batModif.getNom(), NotificationType.SUCCES_EDIT);
            refreshBatimentCards((Div) card.getParent().get());
        });
        editForm.editBatiment(bat);
        add(editForm);
        editForm.open();
    });

    // --- Supprimer
    subMenu.addItem("Supprimer", e -> {
        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle("Confirmer la suppression");

        confirmDialog.add("Voulez-vous vraiment supprimer le bâtiment \"" + bat.getNom() + "\" ?");

        Button confirmButton = new Button("Supprimer", event -> {
            batimentService.deleteBatimentById(bat.getId());
            Notifier.show(bat.getNom(), NotificationType.SUCCES_DELETE);
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

