package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl;

import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityTypeLevelData;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;

import java.util.EnumSet;

public class CAbilityTypeChannelTestLevelData extends CAbilityTypeLevelData {
    private final float artDuration;

    public CAbilityTypeChannelTestLevelData(final EnumSet<CTargetType> targetsAllowed, final float artDuration) {
        super(targetsAllowed);
        this.artDuration = artDuration;
    }

    public float getArtDuration() {
        return this.artDuration;
    }

}
