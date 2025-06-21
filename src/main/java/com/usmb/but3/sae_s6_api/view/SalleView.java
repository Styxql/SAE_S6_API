package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.service.BatimentService;
import com.usmb.but3.sae_s6_api.service.SalleService;
import com.usmb.but3.sae_s6_api.service.TypeSalleService;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;

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

        /*
         * Dialog + Form de creation
         */
        // Add new
        Dialog dialogNewSalle = new Dialog();
        dialogNewSalle.setHeaderTitle("Nouvelle Salle");

        // Fields
        TextField nomFieldNewSalle = new TextField("Nom");
        TextField imageFieldNewSalle = new TextField("URL de l'image");
        IntegerField capaciteFieldNewSalle = new IntegerField("Capacite");

        ComboBox<TypeSalle> typeSalleComboBox = new ComboBox<TypeSalle>("Type Salle");
        typeSalleComboBox.setItems(typeSalleService.getAllTypeSalles());
        typeSalleComboBox.setItemLabelGenerator(TypeSalle::getNom);

        FormLayout formLayoutNewSalle = new FormLayout();
        formLayoutNewSalle.add(nomFieldNewSalle);
        formLayoutNewSalle.add(imageFieldNewSalle);
        formLayoutNewSalle.add(capaciteFieldNewSalle);
        formLayoutNewSalle.add(typeSalleComboBox);
        dialogNewSalle.add(formLayoutNewSalle);

        Button saveButtonNewSalle = new Button("Enregistrer", e -> {
            String nomNewSalle = nomFieldNewSalle.getValue().trim();
            String imageNewSalle = imageFieldNewSalle.getValue().trim();
            Integer capaciteNewSalle = capaciteFieldNewSalle.getValue();
            TypeSalle typeSalleNewSalle = typeSalleComboBox.getValue();

            // Erreur si champs vide
            if (nomNewSalle.isEmpty() || capaciteNewSalle == null || typeSalleNewSalle == null) {
                Notification notification = new Notification();
                notification.setDuration(3000);
                notification.setPosition(Notification.Position.BOTTOM_END);

                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                Span text = new Span("Le nom, la capacite et le type salle sont obligatoire");
                Icon icon = VaadinIcon.WARNING.create();

                HorizontalLayout notificationLayout = new HorizontalLayout(icon, text);
                notificationLayout.setAlignItems(Alignment.CENTER);

                notification.add(notificationLayout);
                notification.open();
                return;
            }

            // Modifie la valeur
            Salle newSalle = new Salle();
            newSalle.setNom(nomNewSalle);
            newSalle.setUrlImg(imageNewSalle);
            newSalle.setCapacite(capaciteNewSalle);
            newSalle.setTypeSalle(typeSalleNewSalle);
            newSalle.setBatiment(batiment);

            // Sauvegardé Type Salle
            salleService.saveSalle(newSalle);
            Notification.show("Salle ajouté avec succès");
            dialogNewSalle.close();
            refreshSalleCards();
        });

        // Bouton Cancel
        Button cancelButtonNew = new Button("Annuler", e -> dialogNewSalle.close());
        dialogNewSalle.getFooter().add(cancelButtonNew, saveButtonNewSalle);

        // Ajout de l'event sur le bouton
        addButton.addClickListener(e -> {
            nomFieldNewSalle.clear();
            imageFieldNewSalle.clear();
            capaciteFieldNewSalle.clear();
            typeSalleComboBox.clear();
            dialogNewSalle.open();
        });

        add(header, cardLayout, dialogNewSalle);
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
                : "images/image.jpg";

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
            Dialog dialogEditSalle = new Dialog();
            dialogEditSalle.setHeaderTitle("Modifier la salle");

            TextField nomFieldEditSalle = new TextField("Nom");
            nomFieldEditSalle.setValue(salle.getNom());

            TextField imageFieldEditSalle = new TextField("URL de l'image");
            imageFieldEditSalle.setValue(salle.getUrlImg() != null ? salle.getUrlImg() : "");

            IntegerField capaciteFieldEditSalle = new IntegerField("Capacité");
            capaciteFieldEditSalle.setValue(salle.getCapacite());

            ComboBox<TypeSalle> typeSalleComboBox = new ComboBox<TypeSalle>("Type Salle");
            typeSalleComboBox.setItems(typeSalleService.getAllTypeSalles());
            typeSalleComboBox.setItemLabelGenerator(TypeSalle::getNom);

            typeSalleComboBox.setValue(salle.getTypeSalle());

            FormLayout formLayout = new FormLayout(nomFieldEditSalle, imageFieldEditSalle, capaciteFieldEditSalle,
                    typeSalleComboBox);
            dialogEditSalle.add(formLayout);

            Button saveButtonEditSalle = new Button("Enregistrer", e2 -> {
                String nomEditSalle = nomFieldEditSalle.getValue().trim();
                String imageEditSalle = imageFieldEditSalle.getValue().trim();
                Integer capaciteEditSalle = capaciteFieldEditSalle.getValue();
                TypeSalle typeSalleEditSalle = typeSalleComboBox.getValue();

                // Erreur si champs vide
                if (nomEditSalle.isEmpty() || imageEditSalle == null || typeSalleEditSalle == null) {
                    Notification notification = new Notification();
                    notification.setDuration(3000);
                    notification.setPosition(Notification.Position.BOTTOM_END);

                    notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                    Span text = new Span("Le nom, la capacite et le type salle sont obligatoire");
                    Icon icon = VaadinIcon.WARNING.create();

                    HorizontalLayout notificationLayout = new HorizontalLayout(icon, text);
                    notificationLayout.setAlignItems(Alignment.CENTER);

                    notification.add(notificationLayout);
                    notification.open();
                    return;
                }

                // Modifie la valeur
                salle.setNom(nomEditSalle);
                salle.setUrlImg(imageEditSalle);
                salle.setCapacite(capaciteEditSalle);
                salle.setTypeSalle(typeSalleEditSalle);

                // Sauvegardé Type Salle
                salleService.saveSalle(salle);
                Notification.show("Salle ajouté avec succès");
                dialogEditSalle.close();
                refreshSalleCards();
            });

            Button cancelButton = new Button("Annuler", e2 -> dialogEditSalle.close());
            dialogEditSalle.getFooter().add(cancelButton, saveButtonEditSalle);

            add(dialogEditSalle);
            dialogEditSalle.open();
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

        headerLayout.add(nameWithIcon, menuBar);
        content.add(image, headerLayout);
        card.add(content);

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
