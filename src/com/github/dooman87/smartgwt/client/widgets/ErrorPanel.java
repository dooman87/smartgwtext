package com.github.dooman87.smartgwt.client.widgets;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Show error with full stack trace(supports cause exceptions)<br/>
 * Panel width compute in {@link ErrorPanel#formatErrorAndSetSize()}.
 *  It gets element in stack trace with max length and width=maxLength * 8
 * <br/>Date: 29.11.2010<br/>
 * @author Dmitry N. Pokidov
 */
public class ErrorPanel extends VLayout {
    private String errorText;
    private Throwable error;

    public ErrorPanel(String errorText, Throwable error) {
        this.errorText = errorText;
        this.error = error;
        initUI();
    }

    protected void initUI() {
        Label errorTextPane = new Label(errorText);
        final Label errorPane = new Label("<pre>" + formatErrorAndSetSize() + "</pre>");
        errorPane.setCanSelectText(true);
        errorPane.setVisible(false);

        HLayout buttons = new HLayout();
        IButton showError = new IButton("Дополнительно");
        showError.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                errorPane.setVisible(!errorPane.isVisible());
            }
        });
        buttons.setAlign(Alignment.RIGHT);
        buttons.setLayoutAlign(Alignment.RIGHT);
        buttons.addMember(new LayoutSpacer());
        buttons.addMember(showError);
        buttons.setHeight(30);

        addMember(errorTextPane);
        addMember(buttons);
        addMember(errorPane);
    }

    protected String formatErrorAndSetSize() {
        int maxWidth = 0;
        Throwable currentError = error;

        StringBuilder builder = new StringBuilder();
        while (currentError != null) {
            if (currentError != error) {
                builder.append("\nCaused by: ");
            }

            builder.append(currentError.getClass().getName());
            builder.append(": ").append(currentError.getMessage());

            for (StackTraceElement e : currentError.getStackTrace()) {
                builder.append("\n  at ");
                String formatStackElement = e.toString();
                int elementWidth = formatStackElement.length() + 4;
                maxWidth = elementWidth > maxWidth ? elementWidth : maxWidth;
                builder.append(formatStackElement);
            }

            currentError = currentError.getCause();
        }

        setWidth(maxWidth * 8);

        return builder.toString();
    }

    public static void inWindow(String errorText, Throwable error) {
        Window wnd = new Window();
        wnd.setTitle("Ошибка");
        ErrorPanel panel = new ErrorPanel(errorText, error);
        panel.setMargin(10);
        wnd.addItem(panel);
        wnd.setIsModal(true);
        wnd.setAutoSize(true);
        wnd.setAutoCenter(true);
        wnd.setShowMinimizeButton(false);
        wnd.show();
    }
}
