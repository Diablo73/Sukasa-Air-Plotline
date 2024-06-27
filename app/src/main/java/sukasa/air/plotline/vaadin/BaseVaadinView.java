package sukasa.air.plotline.vaadin;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLink;

@RoutePrefix("/vaa")
public class BaseVaadinView extends VerticalLayout {

    public BaseVaadinView() {
        RouterLink homeLink = new RouterLink("Home", HomeView.class);
        RouterLink loginLink = new RouterLink("Login", LoginView.class);
        RouterLink statusLink = new RouterLink("Status", StatusView.class);
        RouterLink fetchAllLink = new RouterLink("FetchAll", FetchAllView.class);

        HorizontalLayout header = new HorizontalLayout(homeLink, loginLink, statusLink, fetchAllLink);
        add(header);
    }
}
