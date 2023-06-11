package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl;

import com.etheller.warsmash.util.War3ID;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CSimulation;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.CAbility;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.generic.CLevelingAbility;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.item.CAbilityItemManaRegain;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityType;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityTypeLevelData;

import java.util.List;

public class CAbilityTypeItemManaRegain extends CAbilityType<CAbilityTypeItemManaRegainLevelData> {

    public CAbilityTypeItemManaRegain(final War3ID alias, final War3ID code,
                                      final List<CAbilityTypeItemManaRegainLevelData> levelData) {
        super(alias, code, levelData);
    }

    @Override
    public CAbility createAbility(final int handleId) {
        final CAbilityTypeItemManaRegainLevelData levelData = getLevelData(0);
        return new CAbilityItemManaRegain(handleId, getAlias(), levelData.getManaToRegain());
    }

    @Override
    public void setLevel(final CSimulation game, final CLevelingAbility existingAbility, final int level) {

        final CAbilityTypeLevelData levelData = getLevelData(level - 1);

        // TODO ignores fields

        (existingAbility).setLevel(level);

    }
}
