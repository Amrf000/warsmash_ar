package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.impl;

import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.types.CAbilityTypeLevelData;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;

import java.util.EnumSet;

public class CAbilityTypeReturnResourcesLevelData extends CAbilityTypeLevelData {

    private final boolean acceptsGold;
    private final boolean acceptsLumber;

    public CAbilityTypeReturnResourcesLevelData(final EnumSet<CTargetType> targetsAllowed, final boolean acceptsGold,
                                                final boolean acceptsLumber) {
        super(targetsAllowed);
        this.acceptsGold = acceptsGold;
        this.acceptsLumber = acceptsLumber;
    }

    public boolean isAcceptsGold() {
        return this.acceptsGold;
    }

    public boolean isAcceptsLumber() {
        return this.acceptsLumber;
    }

}
