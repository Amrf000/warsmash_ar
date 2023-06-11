package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types;

import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;

import java.util.EnumSet;

public class CAbilityTypeLevelData {
    private final EnumSet<CTargetType> targetsAllowed;

    public CAbilityTypeLevelData(final EnumSet<CTargetType> targetsAllowed) {
        this.targetsAllowed = targetsAllowed;
    }

    public EnumSet<CTargetType> getTargetsAllowed() {
        return this.targetsAllowed;
    }
}
