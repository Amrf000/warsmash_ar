package com.etheller.warsmash.desktop.editor.mdx;

import com.etheller.warsmash.desktop.DesktopLauncher;
import com.etheller.warsmash.desktop.editor.mdx.ui.YseraFrame;
import com.etheller.warsmash.units.DataTable;
import com.etheller.warsmash.viewer5.handlers.mdx.MdxHandler.ShaderEnvironmentType;
import com.etheller.warsmash.viewer5.handlers.mdx.MdxViewer;

import javax.swing.*;

public class MdxEditorMain {

    public static void main(final String[] args) {
        DesktopLauncher.loadExtensions();
        MdxViewer.DEFAULT_SHADER_ENV = ShaderEnvironmentType.GAME;

        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception ignored) {
        }

        final DataTable warsmashIni = DesktopLauncher.loadWarsmashIni();

        SwingUtilities.invokeLater(() -> {
            final YseraFrame frame = new YseraFrame(warsmashIni);
            frame.setVisible(true);
        });
    }

}
