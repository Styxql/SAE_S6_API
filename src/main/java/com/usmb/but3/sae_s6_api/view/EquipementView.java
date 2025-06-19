package com.usmb.but3.sae_s6_api.view;

import com.usmb.but3.sae_s6_api.entity.Equipement;
import com.usmb.but3.sae_s6_api.service.EquipementService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.util.StringUtils;

@Route(value = "equipement")
@PageTitle("Equipements")
@Menu(title = "Equipements", order = 0, icon = "vaadin:clipboard-check")
public class EquipementView extends VerticalLayout {
	private final EquipementService equipementService;

	final Grid<Equipement> grid;

	final TextField filter;

	private final Button addNewBtn;

	public EquipementView(EquipementService equipementService, EquipementEditor editor) {
		this.equipementService = equipementService;

		this.grid = new Grid<>(Equipement.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Ajouter un équipement", VaadinIcon.PLUS.create());

		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid,editor);

		grid.setHeight("300px");
		grid.setColumns("id", "nom", "hauteur", "longueur", "largeur", "urlImg");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
		filter.setPlaceholder("Filtrer par type");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listEquipement(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.edit(e.getValue());
		});
			// Instantiate and edit new Customer the new button is clicked
			addNewBtn.addClickListener(e -> editor.edit(new Equipement(null,"",null,null,null,null,null,null)));
	
			// Listen changes made by the editor, refresh data from backend
			editor.setChangeHandler(() -> {
				editor.setVisible(false);
				listEquipement(filter.getValue());
			});
	
			// Initialize listing
			listEquipement(null);

		addNewBtn.addClickListener(e -> editor.edit(new Equipement(null,"",null,null,null,null,null,null)));
		editor.setChangeHandler(() -> {
		editor.setVisible(false);
		listEquipement(filter.getValue());
		});

		// Initialize listing
		listEquipement(null);
	}

	void listEquipement(String filterText) {
		
		grid.setItems(equipementService.getAllEquipements());
		
	}

}
