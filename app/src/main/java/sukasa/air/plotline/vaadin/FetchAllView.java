package sukasa.air.plotline.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import sukasa.air.plotline.models.SeatInfo;
import sukasa.air.plotline.models.responses.FetchAllResponse;
import sukasa.air.plotline.service.SeatQueryService;

@Route("fetchAll")
public class FetchAllView extends BaseVaadinView {

    public FetchAllView(@Autowired SeatQueryService seatQueryService) {

        add(new H1("FetchAllView"));

        FetchAllResponse fetchAllResponse = seatQueryService.fetchAll();
        Grid<SeatInfo> grid = new Grid<>(SeatInfo.class);
        grid.setItems(fetchAllResponse.getSeatInfoList());
        grid.setPageSize(2);
        add(grid);
    }
}
