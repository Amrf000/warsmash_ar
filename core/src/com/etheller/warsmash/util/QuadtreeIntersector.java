package com.etheller.warsmash.util;

public interface QuadtreeIntersector<T> {
    /**
     * Handles what to do when the intersector finds an intersecting object,
     * returning true if we should stop the intersection test and process no more
     * objects.
     *
     */
    boolean onIntersect(T intersectingObject);
}
