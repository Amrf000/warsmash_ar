package com.etheller.warsmash.viewer5.handlers.w3x.simulation.behaviors;

import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CSimulation;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.CUnit;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.targeting.AbilityTarget;

public abstract class CAbstractRangedBehavior implements CRangedBehavior {
    protected final CUnit unit;
    protected AbilityTarget target;
    private boolean wasWithinPropWindow = false;

    private boolean disableMove = false;
    private CBehaviorMove moveBehavior;

    public CAbstractRangedBehavior(final CUnit unit) {
        this.unit = unit;
    }

    protected final CAbstractRangedBehavior innerReset(final AbilityTarget target) {
        return innerReset(target, false);
    }

    protected final CAbstractRangedBehavior innerReset(final AbilityTarget target, final boolean disableCollision) {
        this.target = target;
        this.wasWithinPropWindow = false;
        CBehaviorMove moveBehavior;
        if (this.unit.isMovementDisabled()) {
            moveBehavior = this.unit.getMoveBehavior().reset(this.target, this, disableCollision);
        } else {
            moveBehavior = null;
        }
        this.moveBehavior = moveBehavior;
        return this;
    }

    protected abstract CBehavior update(CSimulation simulation, boolean withinFacingWindow);

    protected abstract CBehavior updateOnInvalidTarget(CSimulation simulation);

    protected abstract boolean checkTargetStillValid(CSimulation simulation);

    protected abstract void resetBeforeMoving(CSimulation simulation);

    @Override
    public final CBehavior update(final CSimulation simulation) {
        if (!checkTargetStillValid(simulation)) {
            return updateOnInvalidTarget(simulation);
        }
        if (!isWithinRange(simulation)) {
            if ((this.moveBehavior == null) || this.disableMove) {
                return this.unit.pollNextOrderBehavior(simulation);
            }
            resetBeforeMoving(simulation);
            return this.unit.getMoveBehavior();
        }
        if (this.unit.isMovementDisabled()) {
            final float prevX = this.unit.getX();
            final float prevY = this.unit.getY();
            final float deltaX = this.target.getX() - prevX;
            final float deltaY = this.target.getY() - prevY;
            final double goalAngleRad = Math.atan2(deltaY, deltaX);
            float goalAngle = (float) Math.toDegrees(goalAngleRad);
            if (goalAngle < 0) {
                goalAngle += 360;
            }
            float facing = this.unit.getFacing();
            float delta = goalAngle - facing;
            final float propulsionWindow = simulation.getGameplayConstants().getAttackHalfAngle();
            final float turnRate = simulation.getUnitData().getTurnRate(this.unit.getTypeId());

            if (delta < -180) {
                delta = 360 + delta;
            }
            if (delta > 180) {
                delta = -360 + delta;
            }
            final float absDelta = Math.abs(delta);

            if ((absDelta <= 1.0) && (absDelta != 0)) {
                this.unit.setFacing(goalAngle);
            } else {
                float angleToAdd = Math.signum(delta) * (float) Math.toDegrees(turnRate);
                if (absDelta < Math.abs(angleToAdd)) {
                    angleToAdd = delta;
                }
                facing += angleToAdd;
                this.unit.setFacing(facing);
            }
            // If this happens, the unit is facing the wrong way, and has to turn before
            // moving.
            this.wasWithinPropWindow = absDelta < propulsionWindow;
        } else {
            this.wasWithinPropWindow = true;
        }

        return update(simulation, this.wasWithinPropWindow);
    }

    public void setDisableMove(final boolean disableMove) {
        this.disableMove = disableMove;
    }

}
