package com.etheller.warsmash.viewer5;

public interface SceneLightManager {
    void add(final SceneLightInstance lightInstance);

    void remove(final SceneLightInstance lightInstance);

    void update();
}
