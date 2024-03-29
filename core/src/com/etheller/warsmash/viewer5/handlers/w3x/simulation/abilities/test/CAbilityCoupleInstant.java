package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.test;

import com.badlogic.gdx.math.Rectangle;
import com.etheller.warsmash.util.War3ID;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CSimulation;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CUnit;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CUnitEnumFunction;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CWidget;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.CAbility;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.generic.AbstractGenericSingleIconNoSmartActiveAbility;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.targeting.AbilityPointTarget;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.behaviors.CBehavior;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.behaviors.test.CBehaviorCoupleInstant;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat.CTargetType;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.orders.COrderTargetWidget;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.orders.OrderIds;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CPlayer;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.util.AbilityActivationReceiver;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.util.AbilityTargetCheckReceiver;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.util.ResourceType;

import java.util.EnumSet;

public class CAbilityCoupleInstant extends AbstractGenericSingleIconNoSmartActiveAbility {

    private War3ID resultingUnitType;
    private War3ID partnerUnitType;
    private float castRange;
    private float area;
    private EnumSet<CTargetType> targetsAllowed;
    private CBehaviorCoupleInstant behaviorCoupleInstant;
    private int goldCost;
    private int lumberCost;

    public CAbilityCoupleInstant(final int handleId, final War3ID alias, final War3ID resultingUnitType, final War3ID partnerUnitType, final boolean moveToPartner, final float castRange, final float area, final EnumSet<CTargetType> targetsAllowed, final int goldCost, final int lumberCost) {
        super(handleId, alias);
        this.resultingUnitType = resultingUnitType;
        this.partnerUnitType = partnerUnitType;
        this.castRange = castRange;
        this.area = area;
        this.targetsAllowed = targetsAllowed;
        this.goldCost = goldCost;
        this.lumberCost = lumberCost;
    }

    @Override
    public int getBaseOrderId() {
        return OrderIds.coupleinstant;
    }

    @Override
    public boolean isToggleOn() {
        return false;
    }

    @Override
    public void onAdd(final CSimulation game, final CUnit unit) {
        this.behaviorCoupleInstant = new CBehaviorCoupleInstant(unit, this);
    }

    @Override
    public void onRemove(final CSimulation game, final CUnit unit) {

    }

    @Override
    public void onTick(final CSimulation game, final CUnit unit) {

    }

    @Override
    public void onCancelFromQueue(final CSimulation game, final CUnit unit, final int orderId) {

    }

    @Override
    public CBehavior begin(final CSimulation game, final CUnit caster, final int orderId, final CWidget target) {
        // only from engine, not ever allowed by the checks
        if (target instanceof CUnit) {
            return this.behaviorCoupleInstant.reset((CUnit) target);
        }
        return caster.pollNextOrderBehavior(game);
    }

    @Override
    public CBehavior begin(final CSimulation game, final CUnit caster, final int orderId, final AbilityPointTarget point) {
        return caster.pollNextOrderBehavior(game);
    }

    @Override
    public CBehavior beginNoTarget(final CSimulation game, final CUnit caster, final int orderId) {
        final PossiblePairFinderEnum possiblePairFinder = new PossiblePairFinderEnum(caster);
        game.getWorldCollision().enumUnitsInRect(new Rectangle(caster.getX() - this.area, caster.getY() - this.area, this.area * 2, this.area * 2), possiblePairFinder);
        final CUnit coupleTarget = possiblePairFinder.pairMatchFound;
        if (coupleTarget == null) {
            game.getCommandErrorListener().showUnableToFindCoupleTargetError(caster.getPlayerIndex());
            return caster.pollNextOrderBehavior(game);
        }
        coupleTarget.order(game, new COrderTargetWidget(possiblePairFinder.pairMatchAbility.getHandleId(), possiblePairFinder.pairMatchAbility.getBaseOrderId(), caster.getHandleId(), false), false);
        return this.behaviorCoupleInstant.reset(coupleTarget);
    }

    @Override
    protected void innerCheckCanTarget(final CSimulation game, final CUnit unit, final int orderId, final CWidget target, final AbilityTargetCheckReceiver<CWidget> receiver) {
        receiver.targetOk(target);
    }

    @Override
    protected void innerCheckCanTarget(final CSimulation game, final CUnit unit, final int orderId, final AbilityPointTarget target, final AbilityTargetCheckReceiver<AbilityPointTarget> receiver) {
        receiver.orderIdNotAccepted();
    }

    @Override
    protected void innerCheckCanTargetNoTarget(final CSimulation game, final CUnit unit, final int orderId, final AbilityTargetCheckReceiver<Void> receiver) {
        receiver.targetOk(null);
    }

    @Override
    protected void innerCheckCanUse(final CSimulation game, final CUnit unit, final int orderId, final AbilityActivationReceiver receiver) {
        final CPlayer player = game.getPlayer(unit.getPlayerIndex());
        if (player.getGold() >= this.goldCost) {
            if (player.getLumber() >= this.lumberCost) {
                receiver.useOk();
            } else {
                receiver.notEnoughResources(ResourceType.LUMBER);
            }
        } else {
            receiver.notEnoughResources(ResourceType.GOLD);
        }
    }

    public float getCastRange() {
        return this.castRange;
    }

    public void setCastRange(final float castRange) {
        this.castRange = castRange;
    }

    public float getArea() {
        return this.area;
    }

    public void setArea(final float area) {
        this.area = area;
    }

    public EnumSet<CTargetType> getTargetsAllowed() {
        return this.targetsAllowed;
    }

    public void setTargetsAllowed(final EnumSet<CTargetType> targetsAllowed) {
        this.targetsAllowed = targetsAllowed;
    }

    public War3ID getResultingUnitType() {
        return this.resultingUnitType;
    }

    public void setResultingUnitType(final War3ID resultingUnitType) {
        this.resultingUnitType = resultingUnitType;
    }

    public int getGoldCost() {
        return this.goldCost;
    }

    public void setGoldCost(final int goldCost) {
        this.goldCost = goldCost;
    }

    public int getLumberCost() {
        return this.lumberCost;
    }

    public void setLumberCost(final int lumberCost) {
        this.lumberCost = lumberCost;
    }

    @Override
    public int getUIGoldCost() {
        return this.goldCost;
    }

    @Override
    public int getUILumberCost() {
        return this.lumberCost;
    }

    public void setPartnerUnitType(final War3ID partnerUnitType) {
        this.partnerUnitType = partnerUnitType;
    }

    private final class PossiblePairFinderEnum implements CUnitEnumFunction {
        private final CUnit unit;
        private CUnit pairMatchFound = null;
        private CAbilityCoupleInstant pairMatchAbility;

        private PossiblePairFinderEnum(final CUnit unit) {
            this.unit = unit;
        }

        @Override
        public boolean call(final CUnit otherUnit) {
            if (otherUnit.getPlayerIndex() == this.unit.getPlayerIndex()) {
                for (final CAbility ability : otherUnit.getAbilities()) {
                    if (ability instanceof CAbilityCoupleInstant) {
                        final CAbilityCoupleInstant otherCoupleInstant = (CAbilityCoupleInstant) ability;
                        if (otherCoupleInstant.partnerUnitType.equals(this.unit.getTypeId())) {
                            if (CAbilityCoupleInstant.this.partnerUnitType.equals(otherUnit.getTypeId())) {
                                // we're a pair, make sure other unit is not already actively pairing
                                if (!(otherUnit.getCurrentBehavior() instanceof CBehaviorCoupleInstant)) {
                                    if (otherUnit.distance(this.unit) <= CAbilityCoupleInstant.this.area) {
                                        this.pairMatchFound = otherUnit;
                                        this.pairMatchAbility = otherCoupleInstant;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return this.pairMatchFound != null;
        }
    }
}
