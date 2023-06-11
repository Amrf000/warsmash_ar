package com.etheller.warsmash.desktop.editor.mdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.badlogic.gdx.math.Vector3;
import com.etheller.warsmash.WarsmashPreviewApplication;
import com.etheller.warsmash.desktop.editor.mdx.listeners.YseraGUIListener;
import com.etheller.warsmash.desktop.editor.util.ExceptionPopup;
import com.etheller.warsmash.viewer5.handlers.w3x.camera.PortraitCameraManager;
import com.hiveworkshop.rms.parsers.mdlx.MdlxModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class YseraPanel extends JPanel {
    private final WarsmashPreviewApplication warsmashPreviewApplication;
    private final JFileChooser userFileChooser = new JFileChooser();
    private final YseraGUIListener.YseraGUINotifier notifier = new YseraGUIListener.YseraGUINotifier();

    private MdlxModel model;

    private AnimationControllerFrame animationControllerFrame;

    public YseraPanel(final WarsmashPreviewApplication warsmashPreviewApplication) {
        this.warsmashPreviewApplication = warsmashPreviewApplication;
        setLayout(new BorderLayout());
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL30 = true;
        config.gles30ContextMajorVersion = 3;
        config.gles30ContextMinorVersion = 3;
        final LwjglCanvas lwjglCanvas = new LwjglCanvas(warsmashPreviewApplication, config);
        add(BorderLayout.CENTER, lwjglCanvas.getCanvas());
        setPreferredSize(new Dimension(640, 480));
        this.userFileChooser
                .setFileFilter(new FileNameExtensionFilter("Warcraft III Model or Texture", "mdx", "mdl", "blp"));

        final CameraMouseHandler cameraMouseHandler = new CameraMouseHandler(warsmashPreviewApplication);
        Gdx.input.setInputProcessor(cameraMouseHandler);

    }

    public JMenuBar createJMenuBar(final JFrame frame) {
        final JMenuBar jMenuBar = new JMenuBar();

        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> {
            try {
                final int userResult = YseraPanel.this.userFileChooser.showOpenDialog(frame);
                if (userResult == JFileChooser.APPROVE_OPTION) {
                    final File selectedFile = YseraPanel.this.userFileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        YseraPanel.this.model = YseraPanel.this.warsmashPreviewApplication
                                .loadCustomModel(selectedFile.getPath());
                        YseraPanel.this.notifier.openModel(YseraPanel.this.model);
                    }
                }
            } catch (final Exception exc) {
                ExceptionPopup.display(exc);
            }
        });
        fileMenu.add(openItem);
        jMenuBar.add(fileMenu);
        jMenuBar.add(new JMenu("Recent Files"));
        jMenuBar.add(new JMenu("Edit"));
        jMenuBar.add(new JMenu("View"));
        jMenuBar.add(new JMenu("Team Color"));
        final JMenu windowMenu = new JMenu("Windows");
        final JMenuItem modelEditorItem = new JMenuItem("Model Editor");
        windowMenu.add(modelEditorItem);
        final JMenuItem animationControllerItem = new JMenuItem("Animation Controller");
        animationControllerItem.addActionListener(e -> {
            if (YseraPanel.this.animationControllerFrame == null) {
                YseraPanel.this.animationControllerFrame = new AnimationControllerFrame(
                        YseraPanel.this.warsmashPreviewApplication);
                YseraPanel.this.notifier.subscribe(YseraPanel.this.animationControllerFrame);
                YseraPanel.this.animationControllerFrame.setLocationRelativeTo(frame);
            }
            YseraPanel.this.animationControllerFrame.setVisible(true);
            YseraPanel.this.animationControllerFrame.toFront();
        });
        windowMenu.add(animationControllerItem);
        jMenuBar.add(windowMenu);
        jMenuBar.add(new JMenu("Extras"));
        jMenuBar.add(new JMenu("Help"));

        return jMenuBar;
    }

    private static final class CameraMouseHandler implements InputProcessor {
        private final Vector3 screenDimension = new Vector3();
        private final WarsmashPreviewApplication warsmashPreviewApplication;
        private int lastX, lastY;
        private int button;

        public CameraMouseHandler(final WarsmashPreviewApplication warsmashPreviewApplication) {
            this.warsmashPreviewApplication = warsmashPreviewApplication;
        }

        @Override
        public boolean keyDown(final int keycode) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean keyUp(final int keycode) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean keyTyped(final char character) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
            this.lastX = screenX;
            this.lastY = screenY;
            this.button = button;
            return false;
        }

        @Override
        public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
            final int dx = screenX - this.lastX;
            final int dy = screenY - this.lastY;
            final PortraitCameraManager cameraManager = this.warsmashPreviewApplication.getCameraManager();
            if (this.button == Input.Buttons.RIGHT) {
                this.screenDimension.set(-1, 0, 0);
                this.screenDimension.unrotate(cameraManager.camera.viewProjectionMatrix);
                cameraManager.target.add(this.screenDimension.nor().scl(dx * 5));
                this.screenDimension.set(0, 1, 0);
                this.screenDimension.unrotate(cameraManager.camera.viewProjectionMatrix);
                cameraManager.target.add(this.screenDimension.nor().scl(dy * 5));
            } else if (this.button == Input.Buttons.LEFT) {
                cameraManager.horizontalAngle -= Math.toRadians(dx);
                cameraManager.verticalAngle -= Math.toRadians(dy);
            }
            this.lastX = screenX;
            this.lastY = screenY;
            return false;
        }

        @Override
        public boolean mouseMoved(final int screenX, final int screenY) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean scrolled(final int amount) {
            final PortraitCameraManager cameraManager = this.warsmashPreviewApplication.getCameraManager();
            cameraManager.distance += amount * 100;
            return false;
        }
    }
}
