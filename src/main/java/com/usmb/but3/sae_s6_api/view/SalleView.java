package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Batiment;
import com.usmb.but3.sae_s6_api.entity.Salle;
import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.service.BatimentService;
import com.usmb.but3.sae_s6_api.service.SalleService;
import com.usmb.but3.sae_s6_api.service.TypeSalleService;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.util.List;

@Route("batiment")
public class SalleView extends VerticalLayout implements HasUrlParameter<Integer>  {

    private final BatimentService batimentService;
    private final SalleService salleService;
    private final TypeSalleService typeSalleService;

    public SalleView(BatimentService batimentService, SalleService salleService, TypeSalleService typeSalleService) {
        this.batimentService = batimentService;
        this.salleService = salleService;
        this.typeSalleService = typeSalleService;
    }

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        System.out.println(parameter);
        if (parameter == null) {
            event.forwardTo("batiment");
            return;
        }

        Batiment batiment = this.batimentService.getBatimentById(parameter);
        
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull(); // N'a pas par défault 100% width
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        H3 title = new H3("Salles du bâtiment : " + batiment.getNom());
        Button addButton = new Button("Ajouter une salle", VaadinIcon.PLUS.create());

        header.add(title, addButton);

        Div cardLayout = new Div();
        cardLayout.getStyle()
            .set("display", "grid")
            .set("grid-template-columns", "repeat(3, 1fr)")
            .set("gap", "20px")
            .set("padding", "10px");
    
    /*
    * Dialog + Form de creation
    */
    // Add new
    Dialog dialogNewSalle = new Dialog();
    dialogNewSalle.setHeaderTitle("Nouvelle Salle");
    
    // Fields
    TextField nomFieldNewSalle = new TextField("Nom");
    TextField imageFieldNewSalle = new TextField("URL de l'image");
    IntegerField capaciteNewSalle = new IntegerField("Capacite");

    ComboBox<TypeSalle> typeSalleComboBox = new ComboBox<TypeSalle>("Type Salle");
    typeSalleComboBox.setItems(typeSalleService.getAllTypeSalles());
    typeSalleComboBox.setItemLabelGenerator(TypeSalle::getNom); 


    // add(header);
    }

}
