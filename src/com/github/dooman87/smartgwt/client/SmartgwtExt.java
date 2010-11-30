package com.github.dooman87.smartgwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.github.dooman87.smartgwt.client.widgets.ErrorPanel;

/**
 * <br/>Date: 29.11.2010<br/>
 *
 * @author Dmitry N. Pokidov
 */
public class SmartgwtExt implements EntryPoint {
    public void onModuleLoad() {
        VLayout mainLayout = new VLayout(5);
        mainLayout.addMember(new Label("<h1>Some extensions for smartgwt</h1><br/><hr/>"));

        HTMLPane errorPanelDescription = new HTMLPane();
        errorPanelDescription.setWidth100();
        errorPanelDescription.setHeight(50);

        errorPanelDescription.setContents("<span style=\"font-size: 18px\">Error panel show error message with full stack trace. <br>" +
                "Also you can use ErrorPanel.inWindow() to show error in standalone modal window</span>");
        mainLayout.addMember(errorPanelDescription);
        try {
            try {
                Integer.parseInt("notint");
            } catch (NumberFormatException e) {
                throw new RuntimeException("Some exception with cause", e);
            }
        } catch (final Throwable t) {
            ErrorPanel errorPanel = new ErrorPanel("Error while perform operations", t);
            errorPanel.setBorder("solid 2px black");
            mainLayout.addMember(errorPanel);
            IButton toWindow = new IButton("Show in window");
            toWindow.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    ErrorPanel.inWindow("Error while perform integer", t);
                }
            });
            mainLayout.addMember(toWindow);
        }

        mainLayout.setWidth100();
        mainLayout.draw();
    }
}
