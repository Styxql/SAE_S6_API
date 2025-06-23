package com.usmb.but3.sae_s6_api.view;

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
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("typesalle")
@PageTitle("Type Salle")
@Menu(title = "TypeSalle", order = 0, icon = "vaadin:grid-big-o")
public class TypeSalleView extends VerticalLayout {

    private final TypeSalleService typeSalleService;

    final Grid<TypeSalle> grid;

    public TypeSalleView(TypeSalleService typeSalleService) {

        this.typeSalleService = typeSalleService;

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de Type Salle");

        Button addButton = new Button("Ajouter un Type Salle", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.grid = new Grid<>(TypeSalle.class);

        grid.setColumns("id", "nom");

        grid.addColumn(new ComponentRenderer<>(typesalle -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();

            /*
             * Action -> event
             */
            Button editButton = new Button(editIcon, e -> {
                TypeSalleEditor editForm = new TypeSalleEditor(typesalleModif -> {
                    typeSalleService.saveTypeSalle(typesalleModif);
                    Notifier.show(typesalleModif.getNom(), NotificationType.SUCCES_EDIT);
                    refreshTypeSalleList();
                });
                editForm.editTypeSalle(typesalle);
                add(editForm);
                editForm.open();
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

        TypeSalleEditor editor = new TypeSalleEditor(typesalle -> {
            typeSalleService.saveTypeSalle(typesalle);
            Notifier.show(typesalle.getNom(), NotificationType.SUCCES_NEW);
            refreshTypeSalleList();
        });

        addButton.addClickListener(e -> {
            editor.editTypeSalle(new TypeSalle());
            add(editor);
            editor.open();
        });
    }

    private void refreshTypeSalleList() {
        grid.setItems(typeSalleService.getAllTypeSalles());
    }

}
