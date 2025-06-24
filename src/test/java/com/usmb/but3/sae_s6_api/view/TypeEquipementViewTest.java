package com.usmb.but3.sae_s6_api.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.usmb.but3.sae_s6_api.entity.TypeEquipement;
import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.view.editor.TypeEquipementEditor;
import com.usmb.but3.sae_s6_api.view.editor.TypeSalleEditor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
@SpringBootTest
public class TypeEquipementViewTest {
    @Autowired
    private TypeEquipementView typeEquipementView;

    Logger logger = Logger.getLogger(TypeEquipementView.class.getName());

    @Test
    void contextLoads() {
        // Vérifie que le bean est bien chargé
        assertNotNull(typeEquipementView);
    }

    @Test
    public void editorAfficheQuandAddButtonClique() {
        UI testUI = new UI();
        UI.setCurrent(testUI); 

        // Ajoute la vue à l'UI
        testUI.add(typeEquipementView);

        TypeEquipementEditor editor = typeEquipementView.editForm;

        assertFalse(editor.isOpened(), "Le dialog doit être fermé au départ.");

        // Simule un clic sur le bouton
        typeEquipementView.addButton.click();

        assertTrue(editor.isOpened(), "Le dialog doit être ouvert après clic sur Ajouter.");
        assertEquals("", editor.nomField.getValue(), "Le champ nom doit être vide.");

        UI.setCurrent(null); // Nettoyage
    }

    @Test
    public void editorAfficheQuandAddNewClicked() {
        UI testUI = new UI();
        UI.setCurrent(testUI);

        // Ajoute la vue à l'UI 
        testUI.add(typeEquipementView);

        TypeEquipementEditor editor = typeEquipementView.editForm;

        assertFalse(editor.isOpened(), "L'éditeur doit être fermé initialement.");

        // Simule un clic sur le bouton "Ajouter un Type Salle"
        typeEquipementView.addButton.click();

        // L'éditeur doit maintenant être ouvert et vide
        assertTrue(editor.isOpened(), "L'éditeur doit être ouvert après clic.");
        assertEquals("", editor.nomField.getValue(), "Le champ nom doit être vide.");

        // Remplit le champ avec un nom fictif
        editor.nomField.setValue("TypeEquipement Test");
        editor.saveButton.click(); // clique sur enregistrer

        // Récupère le dernier élément de la grille
        TypeEquipement dernier = getLastItem(typeEquipementView.grid);

        assertNotNull(dernier);
        assertEquals("TypeEquipement Test", dernier.getNom(), "Le dernier TypeEquipement doit avoir le nom saisi.");

        UI.setCurrent(null); // Nettoyage
    }

    private TypeEquipement getFirstItem(Grid<TypeEquipement> grid) {
        return ((ListDataProvider<TypeEquipement>) grid.getDataProvider()).getItems().iterator().next();
    }

    private TypeEquipement getLastItem(Grid<TypeEquipement> grid) {
        Collection<TypeEquipement> auteurs = ((ListDataProvider<TypeEquipement>) grid.getDataProvider()).getItems();
        List<TypeEquipement> auteurList = new ArrayList<>(auteurs);
        return auteurList.get(auteurList.size() - 1);
    }
}
