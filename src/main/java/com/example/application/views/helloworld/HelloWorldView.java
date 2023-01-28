package com.example.application.views.helloworld;

import com.example.application.views.MainLayout;
import com.vaadin.componentfactory.DateRange;
import com.vaadin.componentfactory.EnhancedDateRangePicker;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.time.LocalDate;
import java.util.Arrays;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PreserveOnRefresh
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView() {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);

        createSimpleDatePicker();
    }

    private void createSimpleDatePicker() {
        Div message = createMessageDiv("simple-picker-message");

        EnhancedDateRangePicker datePicker = new EnhancedDateRangePicker();

        datePicker.addValueChangeListener(
                event -> updateMessage(message, datePicker));

        datePicker.setId("simple-picker");

        datePicker.setValue(new DateRange(LocalDate.now(),LocalDate.now().plusDays(7)));


        add(datePicker, message);
    }

    private Div createMessageDiv(String id) {
        Div message = new Div();
        message.setId(id);
        message.getStyle().set("whiteSpace", "pre");
        return message;
    }

    private void updateMessage(Div message, EnhancedDateRangePicker datePicker) {
        LocalDate selectedStartDate = (datePicker.getValue()==null)?null:datePicker.getValue().getStartDate();
        LocalDate selectedEndDate = (datePicker.getValue()==null)?null:datePicker.getValue().getEndDate();
        if (selectedStartDate != null) {
            String parsers = null;
            if (datePicker.getParsers() != null)
                parsers = Arrays.toString(datePicker.getParsers());
            message.setText(
                    "Start Day: " + selectedStartDate.getDayOfMonth()
                            + "\nStart Month: " + selectedStartDate.getMonthValue()
                            + "\nStart Year: " + selectedStartDate.getYear()
                            + "\nEnd Date: " + (selectedEndDate==null?"":selectedEndDate.getDayOfMonth())
                            + "\nEnd Month: " + (selectedEndDate==null?"":selectedEndDate.getMonthValue())
                            + "\nEnd Year: " + (selectedEndDate==null?"":selectedEndDate.getYear())
                            + "\nLocale: " + datePicker.getLocale()
                            + "\nFormatting pattern: " + datePicker.getPattern()
                            + "\nParsing pattern: " + parsers);
        } else {
            message.setText("No date is selected");
        }
    }

}
