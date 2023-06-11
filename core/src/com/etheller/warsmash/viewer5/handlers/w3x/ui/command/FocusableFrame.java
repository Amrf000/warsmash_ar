package com.etheller.warsmash.viewer5.handlers.w3x.ui.command;

import com.etheller.warsmash.parsers.fdf.frames.UIFrame;

public interface FocusableFrame extends UIFrame {
    boolean isFocusable();

    void onFocusGained();

    void onFocusLost();

    void keyDown(int keycode);

    void keyUp(int keycode);

    void keyTyped(char character);
}
