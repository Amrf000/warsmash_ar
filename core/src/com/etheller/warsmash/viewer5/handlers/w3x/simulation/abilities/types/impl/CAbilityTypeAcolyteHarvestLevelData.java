package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl;

import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityTypeLevelData;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;

import java.util.EnumSet;

public class CAbilityTypeAcolyteHarvestLevelData extends CAbilityTypeLevelData {
    private final float castRange;
    private final float duration;

    public CAbilityTypeAcolyteHarvestLevelData(final EnumSet<CTargetType> targetsAllowed, final float castRange,
                                               final float duration) {
        super(targetsAllowed);
        this.castRange = castRange;
        this.duration = duration;
    }

    public float getCastRange() {
        return this.castRange;
    }

    public float getDuration() {
        return this.duration;
    }

}
