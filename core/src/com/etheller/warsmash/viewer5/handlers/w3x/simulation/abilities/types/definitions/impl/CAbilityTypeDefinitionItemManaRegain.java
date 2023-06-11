package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.definitions.impl;

import com.etheller.warsmash.units.manager.MutableObjectData.MutableGameObject;
import com.etheller.warsmash.util.War3ID;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityType;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.definitions.CAbilityTypeDefinition;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl.CAbilityTypeItemManaRegain;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl.CAbilityTypeItemManaRegainLevelData;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;

import java.util.EnumSet;
import java.util.List;

public class CAbilityTypeDefinitionItemManaRegain
        extends AbstractCAbilityTypeDefinition<CAbilityTypeItemManaRegainLevelData> implements CAbilityTypeDefinition {
    protected static final War3ID MANA_POINTS_GAINED = War3ID.fromString("Impg");

    @Override
    protected CAbilityTypeItemManaRegainLevelData createLevelData(final MutableGameObject abilityEditorData,
                                                                  final int level) {
        final String targetsAllowedAtLevelString = abilityEditorData.getFieldAsString(TARGETS_ALLOWED, level);
        final int hitPointsGained = abilityEditorData.getFieldAsInteger(MANA_POINTS_GAINED, level);
        final EnumSet<CTargetType> targetsAllowedAtLevel = CTargetType.parseTargetTypeSet(targetsAllowedAtLevelString);
        return new CAbilityTypeItemManaRegainLevelData(targetsAllowedAtLevel, hitPointsGained);
    }

    @Override
    protected CAbilityType<?> innerCreateAbilityType(final War3ID alias, final MutableGameObject abilityEditorData,
                                                     final List<CAbilityTypeItemManaRegainLevelData> levelData) {
        return new CAbilityTypeItemManaRegain(alias, abilityEditorData.getCode(), levelData);
    }

}
