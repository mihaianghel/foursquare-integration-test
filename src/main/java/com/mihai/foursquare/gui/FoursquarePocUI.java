package com.mihai.foursquare.gui;

import com.mihai.foursquare.dto.ExploreResponseDTO;
import com.mihai.foursquare.dto.RequestParamsDTO;
import com.mihai.foursquare.dto.ResponseDTO;
import com.mihai.foursquare.service.FoursquareService;
import com.mihai.foursquare.util.Operation;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Definition of the graphical user interface
 */
@Theme("valo")
@Title("Foursquare Integration POC")
@SpringUI(path = "/foursquare/explore")
final class FoursquarePocUI extends UI {

    @Value("#{'${gui.limit.values}'.split(',')}")
    private List<Integer> limitValues;

    @Value("#{'${gui.radius.values}'.split(',')}")
    private List<Integer> radiusValues;

    @Value("${gui.limit.default}")
    private int limitDefault;

    @Value("${gui.radius.default}")
    private int radiusDefault;

    @Autowired
    private FoursquareService foursquareService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        AtomicInteger limit = new AtomicInteger(limitDefault);
        AtomicInteger radius = new AtomicInteger(radiusDefault);

        // Create the field for place and add validator
        TextField tf = new TextField("Location");
        tf.addStyleName(ValoTheme.TEXTAREA_SMALL);
        tf.setIcon(FontAwesome.MAP_MARKER);
        tf.setRequired(true);
        tf.setInputPrompt("e.g. New York");
        tf.addValidator(new StringLengthValidator("The location must have at least 3 characters", 3, 15, false));
        tf.setImmediate(true);

        //Limit dropdown
        ComboBox cb2 = new ComboBox("Result limit", limitValues);
        cb2.addStyleName(ValoTheme.TEXTAREA_SMALL);
        cb2.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
        cb2.setIcon(FontAwesome.LIST);
        cb2.setWidth(90, Unit.PIXELS);
        cb2.setValue(limit.get());
        cb2.setImmediate(true);
        cb2.setNullSelectionAllowed(false);
        cb2.addValueChangeListener(event -> limit.set((Integer) event.getProperty().getValue()));
        
        //Radius dropdown
        ComboBox cb1 = new ComboBox("Radius (m)", radiusValues);
        cb1.addStyleName(ValoTheme.TEXTAREA_SMALL);
        cb1.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
        cb1.setIcon(FontAwesome.COMPASS);
        cb1.setWidth(90, Unit.PIXELS);
        cb1.setValue(radius.get());
        cb1.setImmediate(true);
        cb1.setNullSelectionAllowed(false);
        cb1.addValueChangeListener(event -> radius.set((Integer) event.getProperty().getValue()));

        // Create table, set headers and table properties
        Table t = new Table("Nearby places", null);
        t.addStyleName(ValoTheme.TABLE_SMALL);
        t.setSizeFull();
        t.setVisible(false);
        t.setSelectable(true);
        t.addContainerProperty("Name", String.class, null);
        t.addContainerProperty("Contact Number", String.class, null);
        t.addContainerProperty("Address", String.class, null);
        t.addContainerProperty("Checkins", String.class, null);
        t.setColumnExpandRatio(null, 1);

        // Create search button and attach a click action to it
        Button btn = new Button("Explore");
        btn.addStyleName(ValoTheme.BUTTON_SMALL);
        btn.setIcon(FontAwesome.SEARCH);

		btn.addClickListener(event -> {
			try {
				tf.validate();
				cb1.validate();
				cb2.validate();

                final RequestParamsDTO paramsDTO = new RequestParamsDTO(tf.getValue(),
                        Integer.valueOf(cb1.getValue().toString()), Integer.valueOf(cb2.getValue().toString()), Operation.EXPLORE);

				Collection<ResponseDTO> response = foursquareService.execute(paramsDTO);
				if (!response.isEmpty()) {
					t.removeAllItems();
					t.setVisible(true);

					for (ResponseDTO resp : response) {
                        ExploreResponseDTO exploreOption = (ExploreResponseDTO) resp;
						final Collection<String> address = exploreOption.getAddress();
						final String addressLine = StringUtils.join(address, " ");
						t.addItem(new Object[] { exploreOption.getName(), exploreOption.getContactNumber(),
								addressLine, Integer.toString(exploreOption.getCheckins()) }, null);
					}

				} else {
					t.setVisible(false);
					Notification.show("Please try another search criteria. No venues were found", Notification.Type.WARNING_MESSAGE);
				}
            } catch (Validator.InvalidValueException e) {
                Notification.show("Invalid values. Please try again", Notification.Type.ERROR_MESSAGE);
            }
        });

        tf.addShortcutListener(new Button.ClickShortcut(btn, ShortcutAction.KeyCode.ENTER, null));

        //Page layout
        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.addComponent(new FormLayout(tf));
        hl1.addComponent(new FormLayout(cb1));
        hl1.addComponent(new FormLayout(cb2));
        hl1.addComponent(new FormLayout(btn));
        hl1.setSizeFull();
        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.addComponent(t);
        hl2.setSizeFull();

        VerticalLayout vl = new VerticalLayout();
        vl.addComponents(hl1, hl2);
        setContent(vl);
    }

}
