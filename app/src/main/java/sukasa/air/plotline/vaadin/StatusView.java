package sukasa.air.plotline.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyUpEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.responses.SeatStatusResponse;
import sukasa.air.plotline.service.SeatQueryService;

@Route("status")
public class StatusView extends BaseVaadinView {

    public StatusView(@Autowired SeatQueryService seatQueryService) {

        add(new H1("Status"));

        FormLayout seatNumberFormLayout = new FormLayout();
        seatNumberFormLayout.setWidth(50, Unit.PERCENTAGE);
        VerticalLayout seatStatusResponseVerticalLayout = new VerticalLayout();

        NumberField seatNumberNumberField = new NumberField("SeatNumber", "1");
        seatNumberNumberField.setMin(1);
        seatNumberNumberField.setMax(300);
        seatNumberNumberField.setStep(1);
        seatNumberNumberField.setStepButtonsVisible(true);
        seatNumberNumberField.focus();
        seatNumberFormLayout.addFormItem(new FormItem(seatNumberNumberField), "Press ENTER to search");

        seatNumberNumberField.addKeyUpListener(Key.ENTER, keyUpEvent -> {
            seatStatusResponseVerticalLayout.removeAll();
            SeatStatusResponse seatStatusResponse = statusFormLayoutClickListenerMethod(keyUpEvent, seatQueryService, seatNumberNumberField.getValue().intValue());
            if (StatusEnum.SEAT_RESERVED.getStatusCode().equals(seatStatusResponse.getStatusCode())) {
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("seatNumber", String.valueOf(seatStatusResponse.getSeatInfo().getSeatNumber())));
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("seatStatus", seatStatusResponse.getMessage()));
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("passengerName", seatStatusResponse.getSeatInfo().getPassengerName()));
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("passengerPhone", seatStatusResponse.getSeatInfo().getPassengerPhone()));
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("passengerAge", String.valueOf(seatStatusResponse.getSeatInfo().getPassengerAge())));
            } else {
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("seatNumber", StringUtils.EMPTY));
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("seatStatus", seatStatusResponse.getMessage()));
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("passengerName", StringUtils.EMPTY));
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("passengerPhone", StringUtils.EMPTY));
                seatStatusResponseVerticalLayout.add(createSeatStatusResponseDiv("passengerAge", StringUtils.EMPTY));
            }
        });

        preventLabelWrapping(seatNumberFormLayout);

        add(seatNumberFormLayout);
        add(seatStatusResponseVerticalLayout);
    }

    private SeatStatusResponse statusFormLayoutClickListenerMethod(KeyUpEvent keyUpEvent, SeatQueryService seatQueryService, int seatNumber) {
        return seatQueryService.seatStatus(seatNumber);
    }

    private Div createSeatStatusResponseDiv(String label, String value) {
        Div seatStatusResponseDiv = new Div();
        seatStatusResponseDiv.setWidthFull();
        seatStatusResponseDiv.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("padding", "5px 0");

        Span labelSpan = new Span(label);
        labelSpan.setWidth("150px");
        labelSpan.getStyle().set("font-weight", "bold");

        Span valueSpan = new Span(value);

        seatStatusResponseDiv.add(labelSpan, valueSpan);
        return seatStatusResponseDiv;
    }

    private void preventLabelWrapping(Component parentComponent) {
        for (Component childComponent : parentComponent.getChildren().toList()) {
            if (childComponent.getElement().hasAttribute("slot") && "label".equals(childComponent.getElement().getAttribute("slot"))) {
                childComponent.getStyle().set("white-space", "nowrap");
            }
            preventLabelWrapping(childComponent);
        }
    }
}
