package com.etheller.warsmash.parsers.jass.triggers;

import com.etheller.interpreter.ast.scope.trigger.Trigger;

public class TriggerCondition {
    private final Trigger trigger;
    private final int conditionIndex;

    public TriggerCondition(final Trigger trigger, final int index) {
        this.trigger = trigger;
        this.conditionIndex = index;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }

    public int getConditionIndex() {
        return this.conditionIndex;
    }
}
