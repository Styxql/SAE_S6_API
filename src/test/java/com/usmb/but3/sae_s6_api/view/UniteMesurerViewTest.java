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

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.entity.UniteMesurer;
import com.usmb.but3.sae_s6_api.view.editor.TypeSalleEditor;
import com.usmb.but3.sae_s6_api.view.editor.UniteMesurerEditor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
@SpringBootTest
public class UniteMesurerViewTest {
    
    @Autowired
    private UniteMesurerView uniteMesurerView;

    Logger logger = Logger.getLogger(UniteMesurerViewTest.class.getName());

    @Test
    void contextLoads() {
        // Vérifie que le bean est bien chargé
        assertNotNull(uniteMesurerView);
    }

@Test
public void editorAfficheQuandAddButtonClique() {
    UI testUI = new UI();
    UI.setCurrent(testUI); // suffisant pour de nombreux cas avec `Dialog`

    // Ajoute la vue à l'UI
    testUI.add(uniteMesurerView);

    UniteMesurerEditor editor = uniteMesurerView.editForm;

    assertFalse(editor.isOpened(), "Le dialog doit être fermé au départ.");

    // Simule un clic sur le bouton
    uniteMesurerView.addButton.click();

    assertTrue(editor.isOpened(), "Le dialog doit être ouvert après clic sur Ajouter.");
    assertEquals("", editor.nomField.getValue(), "Le champ nom doit être vide.");
    assertEquals("", editor.symboleField.getValue(), "Le champ symbole doit être vide.");


    UI.setCurrent(null); // Nettoyage
}



   
@Test
public void editorAfficheQuandAddNewClicked() {
    UI testUI = new UI();
    UI.setCurrent(testUI);

    // Ajoute la vue à l'UI (obligatoire pour ouvrir les Dialogs)
    testUI.add(uniteMesurerView);

    UniteMesurerEditor editor = uniteMesurerView.editForm;

    assertFalse(editor.isOpened(), "L'éditeur doit être fermé initialement.");

    // Simule un clic sur le bouton "Ajouter un Type Salle"
    uniteMesurerView.addButton.click();

    // L'éditeur doit maintenant être ouvert et vide
    assertTrue(editor.isOpened(), "L'éditeur doit être ouvert après clic.");
    assertEquals("", editor.nomField.getValue(), "Le champ nom doit être vide.");

    // Remplit le champ avec un nom fictif
    editor.nomField.setValue("Salle Test");
    editor.saveButton.click(); // clique sur enregistrer

    // Récupère le dernier élément de la grille
    UniteMesurer dernier = getLastItem(uniteMesurerView.grid);

    assertNotNull(dernier);
    assertEquals("Salle Test", dernier.getNom(), "Le dernier TypeSalle doit avoir le nom saisi.");

    UI.setCurrent(null); // Nettoyage
}



     private TypeSalle getFirstItem(Grid<TypeSalle> grid) {
        return((ListDataProvider<TypeSalle>) grid.getDataProvider()).getItems().iterator().next();
    }

    private UniteMesurer getLastItem(Grid<UniteMesurer> grid) {
        Collection<UniteMesurer> auteurs = ((ListDataProvider<UniteMesurer>) grid.getDataProvider()).getItems();
        List<UniteMesurer> auteurList = new ArrayList<>(auteurs);
        return auteurList.get(auteurList.size() - 1);
    }
}
