package com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.uidialog;

import java.util.ArrayList;
import java.util.List;

public class JassUIDialog {
    private final List<JassUIDialogButton> buttons = new ArrayList<>();
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void add(final JassUIDialogButton button) {
        this.buttons.add(button);
    }

    public boolean remove(final JassUIDialogButton button) {
        return this.buttons.remove(button);
    }
}
