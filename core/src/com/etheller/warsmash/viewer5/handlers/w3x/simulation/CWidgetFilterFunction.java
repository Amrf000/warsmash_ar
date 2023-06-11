package com.etheller.warsmash.viewer5.handlers.w3x.simulation;

public interface CWidgetFilterFunction {
    CWidgetFilterFunction ACCEPT_ALL = unit -> true;
    CWidgetFilterFunction ACCEPT_ALL_LIVING = unit -> !unit.isDead();

    boolean call(CWidget unit);
}
