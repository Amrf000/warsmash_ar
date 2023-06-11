package com.etheller.warsmash.desktop.editor.mdx.ui;

import com.badlogic.gdx.Gdx;
import com.etheller.warsmash.WarsmashPreviewApplication;
import com.etheller.warsmash.units.DataTable;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class YseraFrame extends JFrame {
    public YseraFrame(final DataTable warsmashIni) {
        super("Warsmash Model Editor");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        final WarsmashPreviewApplication warsmashPreviewApplication = new WarsmashPreviewApplication(warsmashIni);
        final YseraPanel contentPane = new YseraPanel(warsmashPreviewApplication);
        setContentPane(contentPane);
        setJMenuBar(contentPane.createJMenuBar(this));
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(final WindowEvent e) {

            }

            @Override
            public void windowIconified(final WindowEvent e) {

            }

            @Override
            public void windowDeiconified(final WindowEvent e) {

            }

            @Override
            public void windowDeactivated(final WindowEvent e) {

            }

            @Override
            public void windowClosing(final WindowEvent e) {
                Gdx.app.exit();
            }

            @Override
            public void windowClosed(final WindowEvent e) {

            }

            @Override
            public void windowActivated(final WindowEvent e) {

            }
        });
    }

}
