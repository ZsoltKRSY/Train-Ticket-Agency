package ro.ps.proiect.view.layout;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

public class MainLayout extends VerticalLayout implements RouterLayout {

    public MainLayout() {
        HorizontalLayout navigationBar = new HorizontalLayout();
        navigationBar.addClassName("navbar");

        Anchor bileteLink = new Anchor("/tickets", "Tickets");
        Anchor trenuriLink = new Anchor("/trains", "Trains");
        Anchor gariLink = new Anchor("/stations", "Train Stations");

        navigationBar.add(bileteLink, trenuriLink, gariLink);
        this.add(navigationBar);
    }
}
