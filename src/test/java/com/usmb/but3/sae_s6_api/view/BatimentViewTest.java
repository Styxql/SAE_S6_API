package com.usmb.but3.sae_s6_api.view;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.view.editor.BatimentEditor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

@SpringBootTest
public class BatimentViewTest {

    @Autowired
    private BatimentView batimentView;

    Logger logger = Logger.getLogger(BatimentViewTest.class.getName());

    @Test
    void contextLoads() {
        // Vérifie que le bean est bien chargé
        assertNotNull(batimentView);
    }

    @Test
    public void editorAfficheQuandAddButtonClique() {
        UI testUI = new UI();
        UI.setCurrent(testUI); // suffisant pour de nombreux cas avec `Dialog`

        // Ajoute la vue à l'UI
        testUI.add(batimentView);

        BatimentEditor editor = batimentView.editor;
        assertFalse(editor.isOpened(), "Le dialog doit être fermé au départ.");

        assertFalse(editor.isOpened(), "Le dialog doit être fermé au départ.");

        // Simule un clic sur le bouton
        batimentView.addButton.click();

        assertTrue(editor.isOpened(), "Le dialog doit être ouvert après clic sur Ajouter.");
        assertEquals("", editor.nomField.getValue(), "Le champ nom doit être vide.");

        UI.setCurrent(null); // Nettoyage
    }

    // @Test
    // public void ajoutBatimentViaEditor() {
    // UI testUI = new UI();
    // UI.setCurrent(testUI);

    // BatimentView view = new BatimentView(batimentService, null);
    // testUI.add(view);

    // view.addButton.click();

    // BatimentEditor editor = (BatimentEditor) view.getChildren()
    // .filter(c -> c instanceof BatimentEditor)
    // .findFirst().orElse(null);

    // assertNotNull(editor);
    // assertTrue(editor.isOpened());

    // editor.nomField.setValue("Bat Test");
    // editor.urlImgField.setValue("http://test.com/img.jpg");
    // editor.saveButton.click();

    // Batiment dernier = getLastItem(view);
    // assertNotNull(dernier);
    // assertEquals("Bat Test", dernier.getNom());
    // assertEquals("http://test.com/img.jpg", dernier.getUrlImg());

    // UI.setCurrent(null);
    // }

    private TypeSalle getFirstItem(Grid<TypeSalle> grid) {
        return ((ListDataProvider<TypeSalle>) grid.getDataProvider()).getItems().iterator().next();
    }

    private TypeSalle getLastItem(Grid<TypeSalle> grid) {
        Collection<TypeSalle> auteurs = ((ListDataProvider<TypeSalle>) grid.getDataProvider()).getItems();
        List<TypeSalle> auteurList = new ArrayList<>(auteurs);
        return auteurList.get(auteurList.size() - 1);
    }
}
