package com.etheller.warsmash.viewer5.handlers.w3x.simulation.players;

import com.etheller.interpreter.ast.util.CHandle;

public enum CRace implements CHandle {
    HUMAN(1),
    ORC(2),
    UNDEAD(3),
    NIGHTELF(4),
    DEMON(5),
    OTHER(7);

    public static final CRace[] VALUES = {null, HUMAN, ORC, UNDEAD, NIGHTELF, DEMON, null, OTHER};
    private final int id;

    CRace(final int id) {
        this.id = id;
    }

    public static CRace parseRace(final int race) {
        // TODO: this is bad time complexity (slow) but we're only doing it on startup
        for (final CRace raceEnum : values()) {
            if (raceEnum.getId() == race) {
                return raceEnum;
            }
        }
        return null;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public int getHandleId() {
        return getId();
    }
}
