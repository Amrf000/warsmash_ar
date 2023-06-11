package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl;

import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityTypeLevelData;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;

import java.util.EnumSet;

public class CAbilityTypeItemLifeBonusLevelData extends CAbilityTypeLevelData {
    private final int lifeBonus;

    public CAbilityTypeItemLifeBonusLevelData(final EnumSet<CTargetType> targetsAllowed, final int lifeBonus) {
        super(targetsAllowed);
        this.lifeBonus = lifeBonus;
    }

    public int getLifeBonus() {
        return this.lifeBonus;
    }
}
