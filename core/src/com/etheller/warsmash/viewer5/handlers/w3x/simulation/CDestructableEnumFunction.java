package com.etheller.warsmash.viewer5.handlers.w3x.simulation;

public interface CDestructableEnumFunction {
    /**
     * Operates on a destructable, returning true if we should stop execution.
     *
     */
    boolean call(CDestructable destructable);
}
