package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.service.CapteurService;
import com.usmb.but3.sae_s6_api.service.MarqueService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Vue principale affichant les capteurs existants dans un tableau avec filtres et actions.
 */
@Component
@Scope("prototype")
@Route("capteur")
@PageTitle("Capteurs")
@Menu(title = "Capteurs", order = 0, icon = "vaadin:line-bar-chart")
public class CapteurView extends VerticalLayout {

    private final CapteurService capteurService;
    private final MarqueService marqueService;

    final CapteurEditor editor;


    final Grid<Capteur> grid = new Grid<>(Capteur.class);
    final TextField filter = new TextField("Filtrer par nom");
    final MultiSelectComboBox<Marque> marqueFilter = new MultiSelectComboBox<>("Marque");
    final Button addNewBtn = new Button("Ajouter un capteur", VaadinIcon.PLUS.create());

    public CapteurView(CapteurEditor editor,CapteurService capteurService, MarqueService marqueService) {
        this.capteurService = capteurService;
        this.marqueService = marqueService;
        this.editor=editor;
        configureFilters();
        configureGrid();

        HorizontalLayout actions = new HorizontalLayout(filter, marqueFilter, addNewBtn);
        actions.setAlignItems(Alignment.END);

        add(actions, grid);

        addNewBtn.addClickListener(e -> openEditDialog(new Capteur()));
        listCapteur(null);
    }

    /**
     * Configure les champs de filtre de la vue (nom + marques).
     */
    private void configureFilters() {
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCapteur(filter.getValue()));

        marqueFilter.setItems(marqueService.getAllMarques());
        marqueFilter.setItemLabelGenerator(Marque::getNom);
        marqueFilter.addValueChangeListener(e -> listCapteur(filter.getValue()));
    }

    /**
     * Configure l'affichage de la grille de capteurs.
     */
    private void configureGrid() {
        grid.setColumns("id", "nom", "reference", "hauteur", "longueur", "largeur");
        grid.addColumn(cap -> cap.getMarque() != null ? cap.getMarque().getNom() : "")
            .setHeader("Marque");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        grid.addComponentColumn(this::createActionButtons)
            .setHeader("Actions")
            .setFlexGrow(0)
            .setWidth("150px");
    }

    /**
     * Crée les boutons d'action (éditer/supprimer) pour chaque ligne de capteur.
     */
    private HorizontalLayout createActionButtons(Capteur capteur) {
        Button editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.getElement().setProperty("title", "Modifier");
        editButton.addClickListener(e -> openEditDialog(capteur));

        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addThemeName("error");
        deleteButton.getElement().setProperty("title", "Supprimer");
        deleteButton.addClickListener(e -> openDeleteDialog(capteur));

        return new HorizontalLayout(editButton, deleteButton);
    }

    /**
     * Ouvre une boîte de dialogue pour créer ou modifier un capteur.
     */
    private void openEditDialog(Capteur capteur) {
        Dialog dialog = new Dialog(editor);

        editor.edit(capteur);
        editor.setChangeHandler(() -> {
            dialog.close();
            listCapteur(filter.getValue());
            Notification.show("Capteur enregistré", 3000, Notification.Position.BOTTOM_END);
        });

     

        dialog.open();
    }

    /**
     * Ouvre une boîte de dialogue de confirmation de suppression d'un capteur.
     */
    private void openDeleteDialog(Capteur capteur) {
        Dialog confirmDialog = new Dialog(new Span("Confirmer la suppression du capteur \"" + capteur.getNom() + "\" ?"));

        Button confirmBtn = new Button("Confirmer", e -> {
            capteurService.deleteCapteurById(capteur.getId());
            confirmDialog.close();
            listCapteur(filter.getValue());
            Notification.show("Capteur supprimé", 3000, Notification.Position.TOP_END);
        });

        Button cancelBtn = new Button("Annuler", e -> confirmDialog.close());
        confirmDialog.add(new HorizontalLayout(confirmBtn, cancelBtn));

        confirmDialog.open();
    }

    /**
     * Affiche la liste des capteurs filtrée selon les champs nom et marque.
     */
    private void listCapteur(String filterText) {
        List<Capteur> all = capteurService.getAllCapteurs();

        List<Capteur> filtered = all.stream().filter(c -> {
            boolean matchNom = (filterText == null || filterText.isEmpty())
                    || (c.getNom() != null && c.getNom().toLowerCase().contains(filterText.toLowerCase()));

            boolean matchMarque = marqueFilter.isEmpty()
                    || (c.getMarque() != null && marqueFilter.getValue().contains(c.getMarque()));

            return matchNom && matchMarque;
        }).toList();

        grid.setItems(filtered);
    }
}
