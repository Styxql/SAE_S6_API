package com.usmb.but3.sae_s6_api.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.IconSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

@Layout
public final class MainLayout extends AppLayout {

    MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToDrawer(createHeader(), new Scroller(createSideNav()));
    }

    private Div createHeader() {

        Image appLogo = new Image("images/conforto.png", "Logo Conforto");
        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        var appName = new Span("Conforto");
        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.XXLARGE);

        var header = new Div(appLogo, appName);
        header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER);
        header.getStyle().set("cursor", "pointer");

        return header;
    }

    private SideNav createSideNav() {
        SideNav nav = new SideNav();
        nav.addClassNames(Margin.Horizontal.MEDIUM);
        MenuConfiguration.getMenuEntries().forEach(entry -> {
            if (!entry.path().matches("marque|typesalle|typeEquipement|unitemesurer")) {
                nav.addItem(createSideNavItem(entry));
            }
        });

        // Groupe : Paramètres
        SideNavItem parametresGroup = new SideNavItem("Paramètres");
        parametresGroup.setPrefixComponent(new Icon("vaadin", "cog"));

        parametresGroup.addItem(new SideNavItem("Marques", "marque", new Icon("vaadin", "tag")));
        parametresGroup.addItem(new SideNavItem("Types de salle", "typesalle", new Icon("vaadin", "home")));
        parametresGroup.addItem(new SideNavItem("Types d'équipement", "typeequipement", new Icon("vaadin", "archives")));
        parametresGroup.addItem(new SideNavItem("Unité Mesuré", "unitemesurer", new Icon("vaadin", "sun-o")));

        nav.addItem(parametresGroup);


        return nav;
    }

    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.path());
        }
    }
}