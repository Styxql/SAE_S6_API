package com.usmb.but3.sae_s6_api.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.service.TypeSalleService;
import com.usmb.but3.sae_s6_api.view.editor.TypeSalleEditor;
import com.usmb.but3.sae_s6_api.view.notification.NotificationType;
import com.usmb.but3.sae_s6_api.view.notification.Notifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Component
@Scope("prototype")
@Route("typesalle")
@PageTitle("Type Salle")
public class TypeSalleView extends VerticalLayout {

    private final TypeSalleService typeSalleService;
    // rendre editor accessible en champ d'instance (non final car initialisé dans le constructeur)
    TypeSalleEditor editor;
    final Button addButton;
    final Grid<TypeSalle> grid;

    public TypeSalleView(TypeSalleService typeSalleService) {
        this.typeSalleService = typeSalleService;

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de Type Salle");

        this.addButton = new Button("Ajouter un Type Salle", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.grid = new Grid<>(TypeSalle.class);
        grid.setColumns("id", "nom");

        // Initialise l'éditeur une seule fois avec le callback sauvegarde
        this.editor = new TypeSalleEditor(typesalle -> {
            typeSalleService.saveTypeSalle(typesalle);
            Notifier.show(typesalle.getNom(), NotificationType.SUCCES_EDIT);
            refreshTypeSalleList();
            editor.close();
        });
        add(editor);

        // Colonne actions avec boutons éditer et supprimer
        grid.addColumn(new ComponentRenderer<>(typesalle -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();

            Button editButton = new Button(editIcon, e -> {
                editor.editTypeSalle(typesalle);
                editor.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Button deleteButton = new Button(deleteIcon, e -> {
                Dialog confirmDialog = new Dialog();
                confirmDialog.setHeaderTitle("Confirmer la suppression");
                confirmDialog.add("Voulez-vous vraiment supprimer le type salle \"" + typesalle.getNom() + "\" ?");
                Button confirmButton = new Button("Supprimer", event -> {
                    typeSalleService.deleteTypeSalleById(typesalle.getId());
                    Notifier.show(typesalle.getNom(), NotificationType.SUCCES_DELETE);
                    confirmDialog.close();
                    refreshTypeSalleList();
                });
                confirmButton.getStyle().set("color", "red");
                Button cancelButton = new Button("Annuler", event -> confirmDialog.close());
                confirmDialog.getFooter().add(cancelButton, confirmButton);
                add(confirmDialog);
                confirmDialog.open();
            });
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");

        grid.setItems(this.typeSalleService.getAllTypeSalles());

        add(header, grid);

        // Action bouton ajout : ouvrir un nouvel éditeur avec un TypeSalle vide
        addButton.addClickListener(e -> {
            editor.editTypeSalle(new TypeSalle());
            editor.open();
        });
    }

    private void refreshTypeSalleList() {
        grid.setItems(typeSalleService.getAllTypeSalles());
    }

}
