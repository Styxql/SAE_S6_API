package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.service.BatimentService;
import com.usmb.but3.sae_s6_api.service.SalleService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BatimentViewTest {

    @Autowired
    private BatimentService batimentService;

    @Autowired
    private SalleService salleService;

    private BatimentView batimentView;

    @BeforeEach
    public void setup() {
        batimentView = new BatimentView(batimentService, salleService);
    }

    @Test
    void testBatimentViewLoads() {
        assertNotNull(batimentView, "La vue BatimentView doit être instanciée");
    }

    @Test
    void testAjoutNouveauBatimentSansClick() {
        // Simule l'ouverture du Dialog
        Dialog dialog = batimentView.creationDialog;
        assertNotNull(dialog, "Le dialog doit être présent");
    
        // Récupération des champs du formulaire
        TextField nomField = batimentView.();
        TextField urlField = batimentView.getUrlField();
    
        assertNotNull(nomField, "Le champ 'Nom' doit exister");
        assertNotNull(urlField, "Le champ 'URL de l'image' doit exister");
    
        // Remplir les champs comme le ferait un utilisateur
        nomField.setValue("BâtimentTest");
        urlField.setValue("http://image.test");
    
        // Appel direct de la méthode de sauvegarde (sans utiliser click())
        batimentView.saveBatiment();
    
        // Vérification que le bâtiment a bien été ajouté
        List<Batiment> batiments = batimentService.getAllBatiments();
        assertTrue(
            batiments.stream().anyMatch(b ->
                "BâtimentTest".equals(b.getNom()) &&
                "http://image.test".equals(b.getUrlImg())
            ),
            "Le bâtiment ajouté doit exister dans la liste"
        );
    }
    
}
