package com.etheller.warsmash.viewer5.handlers.w3x.simulation.region;

public interface CRegionEnumFunction {
    /**
     * Operates on a region, returning true if we should stop execution.
     *
     */
    boolean call(CRegion region);
}
