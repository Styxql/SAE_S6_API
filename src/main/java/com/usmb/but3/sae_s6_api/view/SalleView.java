package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.service.BatimentService;
import com.usmb.but3.sae_s6_api.service.SalleService;
import com.usmb.but3.sae_s6_api.service.TypeSalleService;
import com.usmb.but3.sae_s6_api.view.editor.SalleEditor;
import com.usmb.but3.sae_s6_api.view.notification.NotificationType;
import com.usmb.but3.sae_s6_api.view.notification.Notifier;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;


import java.util.List;

@Route("batiment")
public class SalleView extends VerticalLayout implements HasUrlParameter<Integer> {

    private final BatimentService batimentService;
    private final SalleService salleService;
    private final TypeSalleService typeSalleService;
    private Batiment batiment;
    private final Div cardLayout = new Div();

    public SalleView(BatimentService batimentService, SalleService salleService, TypeSalleService typeSalleService) {
        this.batimentService = batimentService;
        this.salleService = salleService;
        this.typeSalleService = typeSalleService;
    }

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        if (parameter == null) {
            event.forwardTo("batiment");
            return;
        }

        this.batiment = this.batimentService.getBatimentById(parameter);

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Salles du bâtiment : " + batiment.getNom());
        Button addButton = new Button("Ajouter une salle", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        cardLayout.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(3, 1fr)")
                .set("gap", "20px")
                .set("padding", "10px");

        SalleEditor editor = new SalleEditor(salle -> {
            salleService.saveSalle(salle);
            Notifier.show(salle.getNom(), NotificationType.SUCCES_NEW);
            refreshSalleCards();
        }, typeSalleService.getAllTypeSalles());

        addButton.addClickListener(e -> {
            editor.editSalle(new Salle());
            add(editor);
            editor.open();
        });

        add(header, cardLayout);
        refreshSalleCards();
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

        // --- Header avec nom, salle count et menu
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(HorizontalLayout.JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.getStyle().set("padding", "8px 12px");

        Icon buildingIcon = VaadinIcon.BOOK.create();
        buildingIcon.setSize("20px");

        Span nom = new Span(salle.getNom());
        nom.getStyle().set("font-weight", "bold").set("font-size", "1.1em");

        HorizontalLayout nameWithIcon = new HorizontalLayout(buildingIcon, nom);
        nameWithIcon.setSpacing(true);
        nameWithIcon.setAlignItems(Alignment.CENTER);

        Span capacite = new Span(salle.getCapacite().toString());
        HorizontalLayout capacitehorizontalLayout = new HorizontalLayout(capacite, VaadinIcon.CHILD.create());
        capacitehorizontalLayout.getStyle()
                .set("background-color", "rgba(89, 147, 255, 0.15)")
                .set("border-radius", "12px")
                .set("padding", "4px 10px")
                .set("font-size", "0.85em");

        // Menu 3 points
        MenuBar menuBar = new MenuBar();
        menuBar.getElement()
                .addEventListener("click", e -> {
                })
                .addEventData("event.stopPropagation()");
        MenuItem menuIcon = menuBar.addItem(new Icon(VaadinIcon.ELLIPSIS_DOTS_V));

        SubMenu subMenu = menuIcon.getSubMenu();

        // --- Modifier
        subMenu.addItem("Modifier", e -> {
            SalleEditor editForm = new SalleEditor(salleModif -> {
                salleService.saveSalle(salleModif);
                Notifier.show(salleModif.getNom(), NotificationType.SUCCES_EDIT);
                refreshSalleCards();
            }, typeSalleService.getAllTypeSalles());
            editForm.editSalle(salle);
            add(editForm);
            editForm.open();
        });

        subMenu.addItem("Supprimer", e -> {
            Dialog confirmDialog = new Dialog();
            confirmDialog.setHeaderTitle("Confirmer la suppression");

            confirmDialog.add("Voulez-vous vraiment supprimer la salle \"" + salle.getNom() + "\" ?");

            Button confirmButton = new Button("Supprimer", event -> {
                salleService.deleteSalleById(salle.getId());
                Notification.show("Salle supprimée");
                confirmDialog.close();

                refreshSalleCards();
            });
            confirmButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            Button cancelButton = new Button("Annuler", event -> confirmDialog.close());
            confirmDialog.getFooter().add(cancelButton, confirmButton);

            add(confirmDialog);
            confirmDialog.open();
        });





        headerLayout.add(nameWithIcon, capacitehorizontalLayout, menuBar);
        content.add(image, headerLayout);
        card.add(content);
        // Navigation
        card.getElement().addEventListener("click", e -> {
            getUI().ifPresent(ui -> ui.navigate("salle/" + salle.getId()));
        });      
        card.getStyle().set("cursor", "pointer");
          

        return card;
    }

    private void refreshSalleCards() {
        cardLayout.removeAll();
        List<Salle> salles = salleService.getByBatimentId(this.batiment.getId());
        for (Salle salle : salles) {
            cardLayout.add(createSalleCard(salle));
        }
    }

}
