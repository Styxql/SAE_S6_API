package com.usmb.but3.sae_s6_api.view;

import java.util.List;

import com.usmb.but3.sae_s6_api.entity.TypeSalle;
import com.usmb.but3.sae_s6_api.service.TypeSalleService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("typesalle")
@PageTitle("Type Salle")
@Menu(title = "TypeSalle", icon = "vaadin:grid-big-o") 
public class TypeSalleView extends VerticalLayout {

    private final TypeSalleService typeSalleService;

    final Grid<TypeSalle> grid;

    public TypeSalleView(TypeSalleService typeSalleService)
    {
        this.typeSalleService = typeSalleService;

        this.grid = new Grid<>(TypeSalle.class);
        
        grid.setColumns("id", "nom");
    
        grid.addColumn(typesalle -> {
            return "blep";
        }).setHeader("Actions").setKey("actions");

        grid.setItems(this.typeSalleService.getAllTypeSalles());

        add(grid);
    }

    
}
