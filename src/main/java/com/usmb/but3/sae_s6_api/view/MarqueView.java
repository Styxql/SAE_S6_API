package com.usmb.but3.sae_s6_api.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.service.MarqueService;
import com.usmb.but3.sae_s6_api.view.editor.MarqueEditor;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
@Component
@Scope("prototype")
@Route("marque")
@PageTitle("Marque")
public class MarqueView extends VerticalLayout {

    private final MarqueService marqueService;

    final Grid<Marque> grid;
    Button addButton;
    MarqueEditor editor;

    public MarqueView(MarqueService marqueService) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Liste de Marque");

        this.addButton = new Button("Ajouter une Marque", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        this.editor = new MarqueEditor(marque -> {
            marqueService.saveMarque(marque);
            Notifier.show(marque.getNom(), NotificationType.SUCCES_NEW);
            refreshMarqueList();
        });

        this.addButton.addClickListener(e -> {
            editor.editMarque(new Marque());
            add(editor);
            editor.open();
        });

        this.marqueService = marqueService;

        this.grid = new Grid<>(Marque.class);

        grid.setColumns("id", "nom");

        
        grid.addColumn(new ComponentRenderer<>(marque -> {
            Icon editIcon = VaadinIcon.EDIT.create();
            Icon deleteIcon = VaadinIcon.TRASH.create();
            
            /*
             * Dialog Suppression
             */
            Dialog confirmDialogDelMarque = new Dialog();
            confirmDialogDelMarque.setHeaderTitle("Confirmer la suppression");

            confirmDialogDelMarque.add("Voulez-vous vraiment supprimer le bâtiment \"" + marque.getNom() + "\" ?");

            Button confirmButtonDelMarque = new Button("Supprimer", event -> {
                marqueService.deleteMarqueById(marque.getId());
                Notification.show("Bâtiment supprimé");
                confirmDialogDelMarque.close();
                refreshMarqueList();
            });
            confirmButtonDelMarque.addThemeVariants(ButtonVariant.LUMO_ERROR);

            Button cancelButton = new Button("Annuler", e -> confirmDialogDelMarque.close());

            confirmDialogDelMarque.getFooter().add(cancelButton, confirmButtonDelMarque);

            add(confirmDialogDelMarque, confirmDialogDelMarque);


            /*
             * Action -> event
             */
            Button editButton = new Button(editIcon, e -> {
                MarqueEditor editForm = new MarqueEditor(marqueModif -> {
                    marqueService.saveMarque(marqueModif);
                    Notifier.show(marqueModif.getNom(), NotificationType.SUCCES_EDIT);
                    refreshMarqueList();
                });
                editForm.editMarque(marque);
                add(editForm);
                editForm.open();
            });
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            Button deleteButton = new Button(deleteIcon, e -> {
                confirmDialogDelMarque.open();
            });
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            return new HorizontalLayout(editButton, deleteButton);
        })).setHeader("Actions");

        grid.setItems(this.marqueService.getAllMarques());

        add(header, grid);
    }

    private void refreshMarqueList() {
        grid.setItems(marqueService.getAllMarques());
    }

}
