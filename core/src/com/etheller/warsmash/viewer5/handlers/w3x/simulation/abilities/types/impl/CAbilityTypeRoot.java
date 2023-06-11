package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl;

import com.etheller.warsmash.util.War3ID;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CSimulation;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.CAbility;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.generic.CLevelingAbility;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.root.CAbilityRoot;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityType;

import java.util.List;

public class CAbilityTypeRoot extends CAbilityType<CAbilityTypeRootLevelData> {

    public CAbilityTypeRoot(final War3ID alias, final War3ID code, final List<CAbilityTypeRootLevelData> levelData) {
        super(alias, code, levelData);
    }

    @Override
    public CAbility createAbility(final int handleId) {
        final CAbilityTypeRootLevelData levelData = getLevelData(0);
        return new CAbilityRoot(handleId, getAlias(), levelData.getRootedWeaponsAttackBits(),
                levelData.getUprootedWeaponsAttackBits(), levelData.isRootedTurning(),
                levelData.getUprootedDefenseType(), levelData.getDuration(), levelData.getOffDuration());
    }

    @Override
    public void setLevel(final CSimulation game, final CLevelingAbility existingAbility, final int level) {

        final CAbilityTypeRootLevelData levelData = getLevelData(level - 1);
        final CAbilityRoot heroAbility = (CAbilityRoot) (existingAbility);

        heroAbility.setRootedWeaponsAttackBits(levelData.getRootedWeaponsAttackBits());
        heroAbility.setUprootedWeaponsAttackBits(levelData.getUprootedWeaponsAttackBits());
        heroAbility.setUprootedDefenseType(levelData.getUprootedDefenseType());
        heroAbility.setRootedTurning(levelData.isRootedTurning());
        heroAbility.setDuration(levelData.getDuration());
        heroAbility.setOffDuration(levelData.getOffDuration());

        heroAbility.setLevel(level);

    }
}
