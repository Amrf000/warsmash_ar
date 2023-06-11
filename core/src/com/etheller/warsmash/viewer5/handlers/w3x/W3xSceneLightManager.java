package com.etheller.warsmash.viewer5.handlers.w3x;

import com.etheller.warsmash.viewer5.gl.DataTexture;

public interface W3xSceneLightManager {
    DataTexture getUnitLightsTexture();

    int getUnitLightCount();

    DataTexture getTerrainLightsTexture();

    int getTerrainLightCount();
}
