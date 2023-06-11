package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.definitions.impl;

import com.etheller.warsmash.units.manager.MutableObjectData.MutableGameObject;
import com.etheller.warsmash.util.War3ID;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityType;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.definitions.CAbilityTypeDefinition;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl.CAbilityTypeCargoHold;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl.CAbilityTypeCargoHoldLevelData;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;

import java.util.EnumSet;
import java.util.List;

public class CAbilityTypeDefinitionCargoHold extends AbstractCAbilityTypeDefinition<CAbilityTypeCargoHoldLevelData>
        implements CAbilityTypeDefinition {
    protected static final War3ID CARGO_CAPACITY = War3ID.fromString("Car1");

    @Override
    protected CAbilityTypeCargoHoldLevelData createLevelData(final MutableGameObject abilityEditorData,
                                                             final int level) {
        final String targetsAllowedAtLevelString = abilityEditorData.getFieldAsString(TARGETS_ALLOWED, level);
        final int cargoCapacity = abilityEditorData.getFieldAsInteger(CARGO_CAPACITY, level);
        final float castRange = abilityEditorData.getFieldAsFloat(CAST_RANGE, level);
        final float duration = abilityEditorData.getFieldAsFloat(DURATION, level);

        final EnumSet<CTargetType> targetsAllowedAtLevel = CTargetType.parseTargetTypeSet(targetsAllowedAtLevelString);
        return new CAbilityTypeCargoHoldLevelData(targetsAllowedAtLevel, cargoCapacity, duration);
    }

    @Override
    protected CAbilityType<?> innerCreateAbilityType(final War3ID alias, final MutableGameObject abilityEditorData,
                                                     final List<CAbilityTypeCargoHoldLevelData> levelData) {
        return new CAbilityTypeCargoHold(alias, abilityEditorData.getCode(), levelData);
    }

}
