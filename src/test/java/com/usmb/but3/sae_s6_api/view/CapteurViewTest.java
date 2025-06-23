package com.usmb.but3.sae_s6_api.view;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.usmb.but3.sae_s6_api.entity.Capteur;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

@SpringBootTest
public class CapteurViewTest {

    @Autowired
    private CapteurView capteurView;

    Logger logger = Logger.getLogger(CapteurViewTest.class.getName());

    @Test
    void testCapteurViewLoads() {
        assertNotNull(capteurView, "La vue CapteurView doit être chargée");
    }

    @Test
    public void editorShowsWhenCapteurSelected() {
        Grid<Capteur> grid = capteurView.grid;
        Capteur firstCapteur = getFirstItem(grid);
        logger.info("Premier capteur : " + firstCapteur);
        CapteurEditor editor = capteurView.editor;

        assertFalse(editor.isVisible(), "L'éditeur ne doit pas être visible au départ");
        grid.asSingleSelect().setValue(firstCapteur);
        assertTrue(editor.isVisible(), "L'éditeur doit s'afficher quand un capteur est sélectionné");

        // On peut vérifier ici quelques champs (exemple selon les getters dans Capteur)
        assertEquals(firstCapteur.getNom(), editor.nom.getValue());
    }

    @Test
    public void editorShowsWhenAddNewClicked() {
        CapteurEditor editor = capteurView.editor;

        Capteur newCapteur = new Capteur();
        editor.edit(newCapteur);
    
        assertTrue(editor.isVisible(), "L'éditeur doit être visible après appel à edit()");
        assertEquals("", editor.nom.getValue());
        assertTrue(editor.isVisible(), "L'éditeur doit s'afficher après clic sur 'Ajouter nouveau'");

        // Les champs doivent être vides ou nulls
        assertEquals("", editor.nom.getValue());
        assertEquals("", editor.description.getValue());
        assertEquals("", editor.reference.getValue());
        assertNull(editor.hauteur.getValue());
        assertNull(editor.longueur.getValue());
        assertNull(editor.largeur.getValue());
        assertNull(editor.marque.getValue());

        // Remplissage des champs
        editor.nom.setValue("CapteurTest");
        editor.description.setValue("Description test");
        editor.reference.setValue("REF123");
        editor.hauteur.setValue(BigDecimal.valueOf(10));
        editor.longueur.setValue(BigDecimal.valueOf(20));
        editor.largeur.setValue(BigDecimal.valueOf(5));
        // Supposons que la liste des marques contient au moins une marque
        editor.marque.setValue(editor.marque.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>()).findFirst().orElse(null));

        editor.save.click();

        Capteur lastCapteur = getLastItem(capteurView.grid);
        logger.info("Dernier capteur ajouté : " + lastCapteur);

        assertEquals("CapteurTest", lastCapteur.getNom());
        assertEquals("Description test", lastCapteur.getDescription());
        assertEquals("REF123", lastCapteur.getReference());
        assertEquals(BigDecimal.valueOf(10), lastCapteur.getHauteur());
        assertEquals(BigDecimal.valueOf(20), lastCapteur.getLongueur());
        assertEquals(BigDecimal.valueOf(5), lastCapteur.getLargeur());
        assertEquals(editor.marque.getValue(), lastCapteur.getMarque());
    }

    // Méthode utilitaire pour récupérer le premier élément  la grille
    private Capteur getFirstItem(Grid<Capteur> grid) {
        return ((ListDataProvider<Capteur>) grid.getDataProvider()).getItems().iterator().next();
    }

    // Méthode utilitaire pour récupérer le dernier élément de la grille
    private Capteur getLastItem(Grid<Capteur> grid) {
        Collection<Capteur> items = ((ListDataProvider<Capteur>) grid.getDataProvider()).getItems();
        List<Capteur> list = new ArrayList<>(items);
        return list.get(list.size() - 1);
    }
}
