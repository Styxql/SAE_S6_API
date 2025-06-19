package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.service.TypeSalleService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("typesalle")
@PageTitle("Type Salle")
@Menu(title = "TypeSalle", icon = "vaadin:grid-big-o")
public class TypeSalleView extends VerticalLayout {

    private final TypeSalleService typeSalleService;

    final Grid<TypeSalle> grid;

    public TypeSalleView(TypeSalleService typeSalleService) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de Type Salle");

        Button addButton = new Button("Ajouter un Type Salle", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.typeSalleService = typeSalleService;

        this.grid = new Grid<>(TypeSalle.class);

        grid.setColumns("id", "nom");

        grid.setItems(this.typeSalleService.getAllTypeSalles());

        add(header, grid);

    Dialog dialog = new Dialog();dialog.setHeaderTitle("Nouveau Type Salle");

    TextField nomField = new TextField("Nom");

    FormLayout formLayout = new FormLayout();formLayout.add(nomField);
    dialog.add(formLayout);

    Button saveButton = new Button("Enregistrer", event -> {
        String nom = nomField.getValue().trim();

        if (nom.isEmpty()) {
            Notification notification = new Notification();
            notification.setDuration(3000);
            notification.setPosition(Notification.Position.BOTTOM_END);

            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            Span text = new Span("Le nom est obligatoire");
            Icon icon = VaadinIcon.WARNING.create();

            HorizontalLayout notificationLayout = new HorizontalLayout(icon, text);
            notificationLayout.setAlignItems(Alignment.CENTER);

            notification.add(notificationLayout);
            notification.open();
            return;
        }

        TypeSalle newTypeSalle = new TypeSalle();
        newTypeSalle.setNom(nom);

        typeSalleService.saveTypeSalle(newTypeSalle);
        Notification.show("Type Salle ajouté avec succès");
        dialog.close();

        refreshTypeSalleList();
    });

    Button cancelButton = new Button("Annuler", e -> dialog.close());dialog.getFooter().add(cancelButton,saveButton);

    addButton.addClickListener(e->
    {
        nomField.clear();
        dialog.open();
    });
    }

    private void refreshTypeSalleList() {
        grid.setItems(typeSalleService.getAllTypeSalles());
    }

}
