package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl;

import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityTypeLevelData;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;

import java.util.EnumSet;

public class CAbilityTypeItemDefenseBonusLevelData extends CAbilityTypeLevelData {
    private final int defenseBonus;

    public CAbilityTypeItemDefenseBonusLevelData(final EnumSet<CTargetType> targetsAllowed, final int defenseBonus) {
        super(targetsAllowed);
        this.defenseBonus = defenseBonus;
    }

    public int getDefenseBonus() {
        return this.defenseBonus;
    }
}
