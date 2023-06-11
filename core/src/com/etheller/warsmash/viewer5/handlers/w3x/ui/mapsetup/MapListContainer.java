package com.etheller.warsmash.viewer5.handlers.w3x.ui.mapsetup;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.etheller.warsmash.datasources.DataSource;
import com.etheller.warsmash.parsers.fdf.GameUI;
import com.etheller.warsmash.parsers.fdf.frames.ListBoxFrame;
import com.etheller.warsmash.parsers.fdf.frames.ListBoxFrame.ListBoxSelelectionListener;
import com.etheller.warsmash.parsers.fdf.frames.SimpleFrame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MapListContainer {
    private final ListBoxFrame mapListBox;

    public MapListContainer(final GameUI rootFrame, final Viewport uiViewport, final String containerKey,
                            final DataSource dataSource, final BitmapFont font) {
        SimpleFrame mapListContainer = (SimpleFrame) rootFrame.getFrameByName(containerKey);
        this.mapListBox = (ListBoxFrame) rootFrame.createFrameByType("LISTBOX", "MapListBox", mapListContainer,
                "WITHCHILDREN");
        this.mapListBox.setSetAllPoints(true);
        this.mapListBox.setFrameFont(font);
        final Collection<String> listfile = dataSource.getListfile();
        final List<String> displayItemPaths = new ArrayList<>();
        for (final String file : listfile) {
            if ((file.toLowerCase().endsWith(".w3x") || file.toLowerCase().endsWith(".w3m")) && !file.contains("/")
                    && !file.contains("\\")) {
                displayItemPaths.add(file);
            }
        }
        Collections.sort(displayItemPaths);
        for (final String displayItemPath : displayItemPaths) {
            this.mapListBox.addItem(displayItemPath, rootFrame, uiViewport);
        }
        mapListContainer.add(this.mapListBox);
    }

    public void addSelectionListener(final ListBoxSelelectionListener listener) {
        this.mapListBox.setSelectionListener(listener);
    }

    public String getSelectedItem() {
        return this.mapListBox.getSelectedItem();
    }
}
