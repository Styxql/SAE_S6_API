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

import com.usmb.but3.sae_s6_api.entity.Marque;
import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.view.editor.MarqueEditor;
import com.usmb.but3.sae_s6_api.view.editor.TypeSalleEditor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
@SpringBootTest
public class MarqueViewTest {
      @Autowired
    private MarqueView marqueView;

    Logger logger = Logger.getLogger(MarqueViewTest.class.getName());

    @Test
    void contextLoads() {
        // Vérifie que le bean est bien chargé
        assertNotNull(marqueView);
    }

@Test
public void editorAfficheQuandAddButtonClique() {
    UI testUI = new UI();
    UI.setCurrent(testUI); // suffisant pour de nombreux cas avec `Dialog`

    // Ajoute la vue à l'UI
    testUI.add(marqueView);

    MarqueEditor editor = marqueView.editor;

    assertFalse(editor.isOpened(), "Le dialog doit être fermé au départ.");

    // Simule un clic sur le bouton
    marqueView.addButton.click();

    assertTrue(editor.isOpened(), "Le dialog doit être ouvert après clic sur Ajouter.");
    assertEquals("", editor.nomField.getValue(), "Le champ nom doit être vide.");

    UI.setCurrent(null); // Nettoyage
}



   
@Test
public void editorAfficheQuandAddNewClicked() {
    UI testUI = new UI();
    UI.setCurrent(testUI);

    testUI.add(marqueView);

    MarqueEditor editor = marqueView.editor;

    assertFalse(editor.isOpened(), "L'éditeur doit être fermé initialement.");

    // Simule un clic sur le bouton "Ajouter un Type Salle"
    marqueView.addButton.click();

    // L'éditeur doit maintenant être ouvert et vide
    assertTrue(editor.isOpened(), "L'éditeur doit être ouvert après clic.");
    assertEquals("", editor.nomField.getValue(), "Le champ nom doit être vide.");

    // Remplit le champ avec un nom fictif
    editor.nomField.setValue("Marque test");
    editor.saveButton.click(); // clique sur enregistrer

    // Récupère le dernier élément de la grille
    Marque dernier = getLastItem(marqueView.grid);

    assertNotNull(dernier);
    assertEquals("Marque test", dernier.getNom(), "La dernière marque doit avoir le nom saisi.");

    UI.setCurrent(null); // Nettoyage
}



     private Marque getFirstItem(Grid<Marque> grid) {
        return((ListDataProvider<Marque>) grid.getDataProvider()).getItems().iterator().next();
    }

    private Marque getLastItem(Grid<Marque> grid) {
        Collection<Marque> auteurs = ((ListDataProvider<Marque>) grid.getDataProvider()).getItems();
        List<Marque> auteurList = new ArrayList<>(auteurs);
        return auteurList.get(auteurList.size() - 1);
    }
}
