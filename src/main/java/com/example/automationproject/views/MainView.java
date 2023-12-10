package com.example.automationproject.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("/")
public class MainView extends AppLayout {

    public MainView() {
        DrawerToggle toggle = new DrawerToggle();

        H1 titulo = new H1("Proyecto Final");
        titulo.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, titulo);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();

        tabs.add(createTab("Devices", DevicesView.class),
                createTab("Users", UsersView.class));

        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        return tabs;
    }

    private Tab createTab(String viewName, Object viewClass) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        link.setRoute((Class<? extends Component>) viewClass);
        link.setTabIndex(-1);
        return new Tab(link);
    }

}
