package com.etheller.warsmash.viewer5.handlers.w3x.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.etheller.warsmash.SingleModelScreen;
import com.etheller.warsmash.WarsmashGdxMultiScreenGame;
import com.etheller.warsmash.datasources.DataSource;
import com.etheller.warsmash.parsers.fdf.GameUI;
import com.etheller.warsmash.parsers.fdf.datamodel.FramePoint;
import com.etheller.warsmash.parsers.fdf.frames.SpriteFrame;
import com.etheller.warsmash.parsers.fdf.frames.UIFrame;
import com.etheller.warsmash.parsers.jass.Jass2.RootFrameListener;
import com.etheller.warsmash.units.DataTable;
import com.etheller.warsmash.units.custom.WTS;
import com.etheller.warsmash.util.WarsmashConstants;
import com.etheller.warsmash.util.WorldEditStrings;
import com.etheller.warsmash.viewer5.Scene;
import com.etheller.warsmash.viewer5.handlers.mdx.MdxViewer;
import com.etheller.warsmash.viewer5.handlers.w3x.ui.command.ClickableFrame;
import com.etheller.warsmash.viewer5.handlers.w3x.ui.command.FocusableFrame;
import com.etheller.warsmash.viewer5.handlers.w3x.ui.sound.KeyedSounds;

import java.io.IOException;
import java.io.InputStream;

public class TestUI {
    private static final Vector2 screenCoordsVector = new Vector2();
    private static final boolean ENABLE_NOT_YET_IMPLEMENTED_BUTTONS = false;

    private final DataSource dataSource;
    private final Scene uiScene;
    private final Viewport uiViewport;
    private final MdxViewer viewer;
    private final RootFrameListener rootFrameListener;
    private final float heightRatioCorrection;
    private final String customTOC;
    private GameUI rootFrame;
    private SpriteFrame cursorFrame;
    private ClickableFrame mouseDownUIFrame;
    private ClickableFrame mouseOverUIFrame;
    private FocusableFrame focusUIFrame;
    private KeyedSounds uiSounds;

    public TestUI(final DataSource dataSource, final Viewport uiViewport, final Scene uiScene, final MdxViewer viewer,
                  final WarsmashGdxMultiScreenGame screenManager, final SingleModelScreen menuScreen,
                  final DataTable warsmashIni, final RootFrameListener rootFrameListener, final String customTOC) {
        this.dataSource = dataSource;
        this.uiViewport = uiViewport;
        this.uiScene = uiScene;
        this.viewer = viewer;
        this.rootFrameListener = rootFrameListener;
        this.customTOC = customTOC;

        float widthRatioCorrection = this.getMinWorldWidth() / 1600f;
        this.heightRatioCorrection = this.getMinWorldHeight() / 1200f;
    }

    private static String getStringWithWTS(final WTS wts, String string) {
        if (string.startsWith("TRIGSTR_")) {
            string = wts.get(Integer.parseInt(string.substring(8)));
        }
        return string;
    }

    public float getHeightRatioCorrection() {
        return this.heightRatioCorrection;
    }

    /**
     * Called "main" because this was originally written in JASS so that maps could
     * override it, and I may convert it back to the JASS at some point.
     */
    public void main() {
        // =================================
        // Load skins and templates
        // =================================
        this.rootFrame = new GameUI(this.dataSource, GameUI.loadSkin(this.dataSource, WarsmashConstants.GAME_VERSION),
                this.uiViewport, this.uiScene, this.viewer, 0, WTS.DO_NOTHING);

        this.rootFrameListener.onCreate(this.rootFrame);
        try {
            this.rootFrame.loadTOCFile(this.customTOC);
        } catch (final IOException exc) {
            throw new IllegalStateException(this.customTOC);
        }

        // Create main menu
        UIFrame main = this.rootFrame.createFrame("Main", this.rootFrame);

        this.cursorFrame = (SpriteFrame) this.rootFrame.createFrameByType("SPRITE", "SmashCursorFrame", this.rootFrame,
                "");
        this.rootFrame.setSpriteFrameModel(this.cursorFrame, this.rootFrame.getSkinField("Cursor"));
        this.cursorFrame.setSequence("Normal");
        this.cursorFrame.setZDepth(-1.0f);
        if (WarsmashConstants.CATCH_CURSOR) {
            Gdx.input.setCursorCatched(true);
        }

        // position all
        this.rootFrame.positionBounds(this.rootFrame, this.uiViewport);

        this.loadSounds();
    }

    public void resize() {

    }

    public void render(final SpriteBatch batch, final GlyphLayout glyphLayout) {
        final BitmapFont font = this.rootFrame.getFont();
        final BitmapFont font20 = this.rootFrame.getFont20();
        font.setColor(Color.YELLOW);
        final String fpsString = "FPS: " + Gdx.graphics.getFramesPerSecond();
        glyphLayout.setText(font, fpsString);
        font.draw(batch, fpsString, (this.getMinWorldWidth() - glyphLayout.width) / 2,
                1100 * this.heightRatioCorrection);
        this.rootFrame.render(batch, font20, glyphLayout);
    }

    private float getMinWorldWidth() {
        if (this.uiViewport instanceof ExtendViewport) {
            return ((ExtendViewport) this.uiViewport).getMinWorldWidth();
        }
        return this.uiViewport.getWorldWidth();
    }

    private float getMinWorldHeight() {
        if (this.uiViewport instanceof ExtendViewport) {
            return ((ExtendViewport) this.uiViewport).getMinWorldHeight();
        }
        return this.uiViewport.getWorldHeight();
    }

    public void update(final float deltaTime) {
        if ((this.focusUIFrame != null) && !this.focusUIFrame.isVisibleOnScreen()) {
            this.setFocusFrame(this.getNextFocusFrame());
        }

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        final int minX = this.uiViewport.getScreenX();
        final int maxX = minX + this.uiViewport.getScreenWidth();
        final int minY = this.uiViewport.getScreenY();
        final int maxY = minY + this.uiViewport.getScreenHeight();

        mouseX = Math.max(minX, Math.min(maxX, mouseX));
        mouseY = Math.max(minY, Math.min(maxY, mouseY));
        if (Gdx.input.isCursorCatched()) {
            if (WarsmashConstants.CATCH_CURSOR) {
                Gdx.input.setCursorPosition(mouseX, mouseY);
            }
        }

        screenCoordsVector.set(mouseX, mouseY);
        this.uiViewport.unproject(screenCoordsVector);
        this.cursorFrame.setFramePointX(FramePoint.LEFT, screenCoordsVector.x);
        this.cursorFrame.setFramePointY(FramePoint.BOTTOM, screenCoordsVector.y);
        this.cursorFrame.setSequence("Normal");

    }

    private FocusableFrame getNextFocusFrame() {
        return this.rootFrame.getNextFocusFrame();
    }

    public void touchDown(final int screenX, final int screenY, final float worldScreenY, final int button) {
        screenCoordsVector.set(screenX, screenY);
        this.uiViewport.unproject(screenCoordsVector);
        final UIFrame clickedUIFrame = this.rootFrame.touchDown(screenCoordsVector.x, screenCoordsVector.y, button);
        if (clickedUIFrame != null) {
            if (clickedUIFrame instanceof ClickableFrame) {
                this.mouseDownUIFrame = (ClickableFrame) clickedUIFrame;
                this.mouseDownUIFrame.mouseDown(this.rootFrame, this.uiViewport);
            }
            if (clickedUIFrame instanceof FocusableFrame) {
                final FocusableFrame clickedFocusableFrame = (FocusableFrame) clickedUIFrame;
                if (clickedFocusableFrame.isFocusable()) {
                    this.setFocusFrame(clickedFocusableFrame);
                }
            }
        }
    }

    private void setFocusFrame(final FocusableFrame clickedFocusableFrame) {
        if (this.focusUIFrame != null) {
            this.focusUIFrame.onFocusLost();
        }
        this.focusUIFrame = clickedFocusableFrame;
        if (this.focusUIFrame != null) {
            this.focusUIFrame.onFocusGained();
        }
    }

    public void touchUp(final int screenX, final int screenY, final float worldScreenY, final int button) {
        screenCoordsVector.set(screenX, screenY);
        this.uiViewport.unproject(screenCoordsVector);
        final UIFrame clickedUIFrame = this.rootFrame.touchUp(screenCoordsVector.x, screenCoordsVector.y, button);
        if (this.mouseDownUIFrame != null) {
            if (clickedUIFrame == this.mouseDownUIFrame) {
                this.mouseDownUIFrame.onClick(button);
                this.uiSounds.getSound("GlueScreenClick").play(this.uiScene.audioContext, 0, 0, 0);
            }
            this.mouseDownUIFrame.mouseUp(this.rootFrame, this.uiViewport);
        }
        this.mouseDownUIFrame = null;
    }

    public void touchDragged(final int screenX, final int screenY, final float worldScreenY, final int pointer) {
        this.mouseMoved(screenX, screenY, worldScreenY);
    }

    public void mouseMoved(final int screenX, final int screenY, final float worldScreenY) {
        screenCoordsVector.set(screenX, screenY);
        this.uiViewport.unproject(screenCoordsVector);
        final UIFrame mousedUIFrame = this.rootFrame.getFrameChildUnderMouse(screenCoordsVector.x,
                screenCoordsVector.y);
        if (mousedUIFrame != this.mouseOverUIFrame) {
            if (this.mouseOverUIFrame != null) {
                this.mouseOverUIFrame.mouseExit(this.rootFrame, this.uiViewport);
            }
            if (mousedUIFrame instanceof ClickableFrame) {
                this.mouseOverUIFrame = (ClickableFrame) mousedUIFrame;
                this.mouseOverUIFrame.mouseEnter(this.rootFrame, this.uiViewport);
            } else {
                this.mouseOverUIFrame = null;
            }
        }
    }

    private void loadSounds() {
        WorldEditStrings worldEditStrings = new WorldEditStrings(this.dataSource);
        DataTable uiSoundsTable = new DataTable(worldEditStrings);
        try {
            try (InputStream miscDataTxtStream = this.dataSource.getResourceAsStream("UI\\SoundInfo\\UISounds.slk")) {
                uiSoundsTable.readSLK(miscDataTxtStream);
            }
            try (InputStream miscDataTxtStream = this.dataSource
                    .getResourceAsStream("UI\\SoundInfo\\AmbienceSounds.slk")) {
                uiSoundsTable.readSLK(miscDataTxtStream);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        this.uiSounds = new KeyedSounds(uiSoundsTable, this.dataSource);
    }

    public KeyedSounds getUiSounds() {
        return this.uiSounds;
    }

    public void hide() {
    }

    public void dispose() {
        if (this.rootFrame != null) {
            this.rootFrame.dispose();
        }
    }

    public boolean keyDown(final int keycode) {
        if (this.focusUIFrame != null) {
            this.focusUIFrame.keyDown(keycode);
        }
        return false;
    }

    public boolean keyUp(final int keycode) {
        if (this.focusUIFrame != null) {
            this.focusUIFrame.keyUp(keycode);
        }
        return false;
    }

    public boolean keyTyped(final char character) {
        if (this.focusUIFrame != null) {
            this.focusUIFrame.keyTyped(character);
        }
        return false;
    }

}
