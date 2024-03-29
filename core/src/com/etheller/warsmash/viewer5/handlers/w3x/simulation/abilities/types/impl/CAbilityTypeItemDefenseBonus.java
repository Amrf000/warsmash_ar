package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl;

import com.etheller.warsmash.util.War3ID;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CSimulation;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.CAbility;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.generic.CLevelingAbility;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.item.CAbilityItemDefenseBonus;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityType;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityTypeLevelData;

import java.util.List;

public class CAbilityTypeItemDefenseBonus extends CAbilityType<CAbilityTypeItemDefenseBonusLevelData> {

    public CAbilityTypeItemDefenseBonus(final War3ID alias, final War3ID code,
                                        final List<CAbilityTypeItemDefenseBonusLevelData> levelData) {
        super(alias, code, levelData);
    }

    @Override
    public CAbility createAbility(final int handleId) {
        final CAbilityTypeItemDefenseBonusLevelData levelData = getLevelData(0);
        return new CAbilityItemDefenseBonus(handleId, getAlias(), levelData.getDefenseBonus());
    }

    @Override
    public void setLevel(final CSimulation game, final CLevelingAbility existingAbility, final int level) {

        final CAbilityTypeLevelData levelData = getLevelData(level - 1);

        // TODO ignores fields

        (existingAbility).setLevel(level);

    }
}
