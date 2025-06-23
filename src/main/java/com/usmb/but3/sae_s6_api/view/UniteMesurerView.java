package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.usmb.but3.sae_s6_api.service.UniteMesurerService;
import com.usmb.but3.sae_s6_api.view.editor.UniteMesurerEditor;
import com.usmb.but3.sae_s6_api.view.notification.NotificationType;
import com.usmb.but3.sae_s6_api.view.notification.Notifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("unitemesurer")
@PageTitle("Unite Mesurer")
@Menu(title = "Unité Mesuré", icon = "vaadin:grid-big-o")
public class UniteMesurerView extends VerticalLayout {

    private final UniteMesurerService uniteMesurerService;

    final Grid<UniteMesurer> grid;

    public UniteMesurerView(UniteMesurerService uniteMesurerService) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de UniteMesurer");

        Button addButton = new Button("Ajouter une unité", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.uniteMesurerService = uniteMesurerService;

        this.grid = new Grid<>(UniteMesurer.class);

        grid.setColumns("id", "nom", "symbole");

        
        grid.addColumn(new ComponentRenderer<>(uniteMesurer -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();
            
            /*
             * Dialog Suppression
             */
            Dialog confirmDialogDelUniteMesurer = new Dialog();
            confirmDialogDelUniteMesurer.setHeaderTitle("Confirmer la suppression");

            confirmDialogDelUniteMesurer.add("Voulez-vous vraiment supprimer l'unite mesurer \"" + uniteMesurer.getNom() + "\" ?");

            Button confirmButtonDelUniteMesurer = new Button("Supprimer", event -> {
                uniteMesurerService.deleteUniteMesurerById(uniteMesurer.getId());
                Notification.show("Unite supprimé");
                confirmDialogDelUniteMesurer.close();
                refreshUniteMesurerList();
            });
            confirmButtonDelUniteMesurer.addThemeVariants(ButtonVariant.LUMO_ERROR);

            Button cancelButton = new Button("Annuler", e -> confirmDialogDelUniteMesurer.close());

            confirmDialogDelUniteMesurer.getFooter().add(cancelButton, confirmButtonDelUniteMesurer);

            add(confirmDialogDelUniteMesurer, confirmDialogDelUniteMesurer);


            /*
             * Action -> event
             */
            Button editButton = new Button(editIcon, e -> {
                UniteMesurerEditor editForm = new UniteMesurerEditor(unitemesurerModif -> {
                    uniteMesurerService.saveUniteMesurer(unitemesurerModif);
                    Notifier.show(unitemesurerModif.getNom(), NotificationType.SUCCES_EDIT);
                    refreshUniteMesurerList();
                });
                editForm.editUniteMesurer(uniteMesurer);
                add(editForm);
                editForm.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Button deleteButton = new Button(deleteIcon, e -> {
                confirmDialogDelUniteMesurer.open();
            });
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");

        grid.setItems(this.uniteMesurerService.getAllUniteMesurers());

        add(header, grid);

        UniteMesurerEditor editor = new UniteMesurerEditor(uniteMesurer -> {
            uniteMesurerService.saveUniteMesurer(uniteMesurer);
            Notifier.show(uniteMesurer.getNom(), NotificationType.SUCCES_NEW);
            refreshUniteMesurerList();
        });

        addButton.addClickListener(e -> {
            editor.editUniteMesurer(new UniteMesurer());
            add(editor);
            editor.open();
        });
    }

    private void refreshUniteMesurerList() {
        grid.setItems(uniteMesurerService.getAllUniteMesurers());
    }

}
