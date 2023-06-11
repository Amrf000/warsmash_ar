package com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat;

public enum CDefenseType implements CodeKeyType {
    SMALL,
    MEDIUM,
    LARGE,
    FORT,
    NORMAL,
    HERO,
    DIVINE,
    NONE;

    public static final CDefenseType[] VALUES = values();

    private final String codeKey;

    CDefenseType() {
        this.codeKey = name().charAt(0) + name().substring(1).toLowerCase();
    }

    public static CDefenseType parseDefenseType(final String typeString) {
        final String upperCaseTypeString = typeString.toUpperCase();
        if (upperCaseTypeString.equals("HEAVY")) {
            return LARGE;
        }
        if (upperCaseTypeString.trim().isEmpty()) {
            System.err.println("bad");
        }
        return valueOf(upperCaseTypeString);
    }

    @Override
    public String getCodeKey() {
        return this.codeKey;
    }
}
